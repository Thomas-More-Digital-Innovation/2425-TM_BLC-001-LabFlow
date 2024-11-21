import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

// Update the config for PostCSS
const config = {
  prerender: {
    handleMissingId: 'ignore', // Ignore missing IDs, geeft een cloudflare error
  },
  preprocess: vitePreprocess({
    postcss: true // Enables PostCSS processing, which is required for TailwindCSS
  }),
  

  kit: {
    adapter: adapter()
  }
};

export default config;