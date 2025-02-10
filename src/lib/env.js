import * as z from 'zod';

const envSchema = z.object({
    BASE_URL: z.string().url(),
    DB_KEY: z.string(),
  });
  
  export const env = envSchema.parse({
    BASE_URL: import.meta.env.VITE_BASE_URL,
    DB_KEY: import.meta.env.VITE_DB_KEY,
  });
  