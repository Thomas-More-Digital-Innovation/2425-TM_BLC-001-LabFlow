<script>
	import { isOpen, id } from './store.js';
	import { getContext } from 'svelte';
	const modalId = getContext('modalId');
</script>

<button on:click={() => ($id = modalId)}>
	<slot />
</button>
