import adapter from '@sveltejs/adapter-auto';
import { vitePreprocess } from '@sveltejs/vite-plugin-svelte';

// Update the config for PostCSS
const config = {
  preprocess: vitePreprocess({
    postcss: true // Enables PostCSS processing, which is required for TailwindCSS
  }),

  kit: {
    adapter: adapter()
  }
};

export default config;
