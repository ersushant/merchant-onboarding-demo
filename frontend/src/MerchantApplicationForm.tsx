import { FormEvent, useState } from 'react';

type FormValues = { merchantName: string; email: string; phone: string };
type MerchantResponse = FormValues & { id: number; status: string };
type FieldErrors = Partial<Record<keyof FormValues, string>>;

const emptyForm: FormValues = { merchantName: '', email: '', phone: '' };
const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080';

function validate(values: FormValues): FieldErrors {
  const errors: FieldErrors = {};
  if (!values.merchantName.trim()) errors.merchantName = 'Enter your business name.';
  if (!/^\S+@\S+\.\S+$/.test(values.email)) errors.email = 'Enter a valid email address.';
  if (!/^[0-9+() -]{7,20}$/.test(values.phone)) errors.phone = 'Enter a valid phone number.';
  return errors;
}

export function MerchantApplicationForm() {
  const [values, setValues] = useState<FormValues>(emptyForm);
  const [errors, setErrors] = useState<FieldErrors>({});
  const [serverError, setServerError] = useState('');
  const [success, setSuccess] = useState<MerchantResponse | null>(null);
  const [submitting, setSubmitting] = useState(false);
  // Keep this key for retries of the same form submission; generate a new one
  // only after the API confirms the application was created.
  const [idempotencyKey, setIdempotencyKey] = useState(() => crypto.randomUUID());

  const update = (field: keyof FormValues, value: string) => {
    setValues(current => ({ ...current, [field]: value }));
    setErrors(current => ({ ...current, [field]: undefined }));
    setServerError('');
  };

  async function submit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const validation = validate(values);
    if (Object.keys(validation).length) { setErrors(validation); return; }

    setSubmitting(true); setServerError(''); setSuccess(null);
    try {
      const response = await fetch(`${API_URL}/api/merchants`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Idempotency-Key': idempotencyKey },
        body: JSON.stringify(values),
      });
      const body: unknown = await response.json();
      if (!response.ok) {
        const error = body as { message?: string; error?: string; errors?: FieldErrors };
        setErrors(error.errors ?? {});
        throw new Error(error.message ?? error.error ?? 'We could not submit your application.');
      }
      setSuccess(body as MerchantResponse);
      setValues(emptyForm);
      setIdempotencyKey(crypto.randomUUID());
    } catch (error) {
      setServerError(error instanceof Error ? error.message : 'Unexpected error. Please try again.');
    } finally { setSubmitting(false); }
  }

  if (success) return <main className="card success"><p className="eyebrow">Application received</p><h1>Thanks, {success.merchantName}.</h1><p>Your application reference is <strong>#{success.id}</strong> and is now <strong>{success.status.replaceAll('_', ' ')}</strong>.</p><button onClick={() => setSuccess(null)}>Submit another application</button></main>;

  return <main className="card"><p className="eyebrow">PartnerLink</p><h1>Start your merchant application</h1><p className="intro">We’ll review your details and keep you updated through every stage.</p>
    <form onSubmit={submit} noValidate>
      <label>Business name<input value={values.merchantName} onChange={e => update('merchantName', e.target.value)} aria-invalid={!!errors.merchantName} /></label>
      {errors.merchantName && <small role="alert">{errors.merchantName}</small>}
      <label>Business email<input type="email" value={values.email} onChange={e => update('email', e.target.value)} aria-invalid={!!errors.email} /></label>
      {errors.email && <small role="alert">{errors.email}</small>}
      <label>Phone number<input inputMode="tel" value={values.phone} onChange={e => update('phone', e.target.value)} aria-invalid={!!errors.phone} /></label>
      {errors.phone && <small role="alert">{errors.phone}</small>}
      {serverError && <p className="server-error" role="alert">{serverError}</p>}
      <button type="submit" disabled={submitting}>{submitting ? 'Submitting…' : 'Submit application'}</button>
    </form>
  </main>;
}
