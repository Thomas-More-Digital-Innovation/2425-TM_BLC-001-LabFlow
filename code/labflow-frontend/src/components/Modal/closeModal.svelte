<script>
    import { id } from "./store.js";
</script>

<button type="button" on:click={() => $id = null} aria-label="Close modal">
	<slot />
</button>
