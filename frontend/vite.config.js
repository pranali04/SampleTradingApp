import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/api': 'http://api_gateway:8080',
      '/ws': {
        target: 'http://notification_service:8083',
        ws: true
      },
      '/auth': 'http://api_gateway:8080',
    }
  }
});
