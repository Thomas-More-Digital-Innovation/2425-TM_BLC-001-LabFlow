<script lang="ts">
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	export let showModal = false;
	import { writable } from 'svelte/store';
	import MultiSelect from 'svelte-multiselect';

	let dialog: HTMLDialogElement;

	$: if (showModal && dialog) dialog.showModal();

	// Close the dialog when it loses the `showModal` state
	function closeDialog() {
		dialog.close();
		showModal = false;
	}

	export let waarden: any[] = [];
	export let selectedValues = writable([]);
</script>

<dialog
	bind:this={dialog}
	on:close={closeDialog}
	on:click={(e) => {
		if (e.target === dialog) closeDialog();
	}}
>
	<div class="modal-content">
		<div class="flex flex-row justify-between items-center">
			<p class="font-bold text-xl">Referentiewaardes linken</p>
			<button
				autofocus
				on:click={closeDialog}
				class="w-10 h-10 p-2 mb-3 flex items-center justify-center bg-red-200 rounded-lg"
				><GoX /></button
			>
		</div>
		<label for="fruits">
			<strong>Kies je referentiewaarden:</strong>
		</label>
		<MultiSelect
			id="fruits"
			options={waarden}
			bind:value={$selectedValues}
			placeholder="Referentiewaarden"
		/>
	</div>
</dialog>

<style>
	dialog {
		position: fixed;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		max-width: 90vw;
		max-height: 60vh;
		width: 60em;
		height: 100em;
		border: none;
		border-radius: 0.2em;
		padding: 0;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
	}

	dialog::backdrop {
		background: rgba(0, 0, 0, 0.3);
	}

	.modal-content {
		padding: 1.5em;
	}

	dialog[open] {
		animation: zoom 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
	}

	@keyframes zoom {
		from {
			transform: translate(-50%, -50%) scale(0.95);
		}
		to {
			transform: translate(-50%, -50%) scale(1);
		}
	}

	button {
		display: block;
		margin-top: 1em;
	}
</style>
