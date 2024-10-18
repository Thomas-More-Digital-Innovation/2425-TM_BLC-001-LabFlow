<script>
    import { id } from "./store.js";
</script>

<span on:click={() => $id = null}>
	<slot />
</span>
