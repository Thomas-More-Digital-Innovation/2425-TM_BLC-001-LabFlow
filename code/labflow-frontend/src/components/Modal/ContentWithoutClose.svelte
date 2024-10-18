<!-- Modal without close button -->
<script>
	import { isOpen, id } from "./store.js";
	import { getContext } from "svelte";

	const modalId = getContext("modalId");
</script>

<div>
	<div class="modal" class:hidden={modalId !== $id}>
		<div class="content">
			<slot />
		</div>
	</div>
</div>
<style>
    @import './style.css';
</style>