import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

// Update the config for PostCSS
const config = {
  prerender: {
    handleHttpError: ({ path, referrer, message }) => {
      // ignore deliberate link to shiny 404 page
      // https://svelte.dev/docs/kit/configuration#prerender
      if (path === '/not-found' && referrer === '/blog/how-we-built-our-404-page') {
        return;
      }
    },
  },
  preprocess: vitePreprocess({
    postcss: true // Enables PostCSS processing, which is required for TailwindCSS
  }),
  

  kit: {
    adapter: adapter()
  }
};

export default config;