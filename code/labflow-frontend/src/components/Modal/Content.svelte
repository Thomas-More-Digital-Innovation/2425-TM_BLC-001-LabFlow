<script>
	import { isOpen, id } from "./store.js"
	import { getContext } from "svelte"
	const modalId = getContext("modalId")
</script>
<div>
	<div class="modal" class:hidden={modalId !== $id}>
		<div class="content">
			<button type="button" class="close-button" on:click={() => $id = false} aria-label="Close">&times;</button>
			<slot />
		</div>
	</div>
</div>
<style>
	.modal {
		position: fixed;
		z-index: 9999;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		background-color: rgb(0,0,0); 
		/* opacity */
		background-color: rgba(0,0,0,0.7);
	}
	.content {
		position: relative;
		background-color: #fefefe;
		margin: 15% auto;
		padding: 20px;
		border: 1px solid #888;
		width: 60%;
	}
	.close-button {
		position: absolute;
		top: 0;
		right: 0.5rem;
		color: #aaa;
		font-size: 40px;
		font-weight: bold;
		cursor: pointer;
	}
	.close-button:hover,
	.close-button:focus {
		color: black;
		text-decoration: none;
		cursor: pointer;
	}
	.hidden {
		display: none;	
	}
</style>