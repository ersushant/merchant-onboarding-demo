import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './styles.css';
import { MerchantApplicationForm } from './MerchantApplicationForm';

createRoot(document.getElementById('root')!).render(
  <StrictMode><MerchantApplicationForm /></StrictMode>,
);
