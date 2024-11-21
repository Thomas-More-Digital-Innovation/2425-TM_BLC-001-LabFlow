import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

const config = {
  preprocess: vitePreprocess({
    postcss: true // Enables PostCSS processing, required for TailwindCSS
  }),

  kit: {
    adapter: adapter(),

    prerender: {
      handleHttpError: ({ path, referrer, message }) => {
        // Ignore deliberate link to shiny 404 page
        if (path === '/not-found' && referrer === '/blog/how-we-built-our-404-page') {
          return;
        }
      },
      // https://svelte.dev/docs/kit/configuration#prerender
      handleMissingId: 'ignore', // Suppress errors for missing fragment identifiers, dit geeft een cloudflare error
    },
  }
};

export default config;
