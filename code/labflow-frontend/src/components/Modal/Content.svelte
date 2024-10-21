<script>
	import { isOpen, id } from "./store.js";
	import { getContext } from "svelte";

	const modalId = getContext("modalId");
</script>

<div>
	<div class="modal" class:hidden={modalId !== $id}>
		<div class="content">
			<button 
				type="button" 
				class="close-button mr-3" 
				on:click={() => $id = false} 
				aria-label="Close">&times;
			</button>
			<slot />
		</div>
	</div>
</div>
<style>
    @import './style.css';
</style>