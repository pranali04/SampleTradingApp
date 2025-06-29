import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/auth': 'http://api_gateway',
      '/orders': 'http://api_gateway',
      '/marketdata': 'http://api_gateway',
      '/notifications': {
        target: 'ws://api_gateway',
        ws: true,
      },
    },
  },
});
