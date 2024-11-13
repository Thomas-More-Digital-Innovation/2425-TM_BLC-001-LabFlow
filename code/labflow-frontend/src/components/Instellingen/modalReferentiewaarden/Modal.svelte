<script lang="ts">
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	export let showModal = false;
	import { writable } from 'svelte/store';
	import MultiSelect from 'svelte-multiselect';
	import { getCookie } from '$lib/globalFunctions';
	import { fetchReferentiewaarden } from '$lib/fetchFunctions';
	// @ts-ignore
	import IoMdCheckmarkCircle from 'svelte-icons/io/IoMdCheckmarkCircle.svelte';

	let dialog: HTMLDialogElement;

	$: if (showModal && dialog) dialog.showModal();

	// Close the dialog when it loses the `showModal` state
	function closeDialog() {
		dialog.close();
		showModal = false;
	}
	const token = getCookie('authToken') || '';

	export let waarden: any[] = [];
	export let selectedValues = writable([]);

	let waarde = '';
	async function nieuweReferentiewaarde() {
		try {
			await fetch('http://localhost:8080/api/createreferentiewaarde', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					waarde: waarde
				})
			});
			waarde = '';
			const result = await fetchReferentiewaarden();
			if (result) {
				waarden = result;
			}
		} catch (error) {
			console.error('Test kon niet worden aangemaakt: ', error);
		}
		return;
	}
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
				class="w-10 h-10 p-2 mb-3 flex items-center justify-center bg-red-400 rounded-lg"
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
		<p class="font-bold text-xl mt-16">Aanmaken referentiewaarde</p>
		<div class="flex flex-row justify-between">
			<input
				type="text"
				name="referentiewaarde"
				id="referentiewaarde"
				bind:value={waarde}
				class="rounded-lg text-black bg-gray-200 h-12 w-80 mt-4"
			/>
			<button
				on:click={nieuweReferentiewaarde}
				type="button"
				class="bg-green-500 rounded-lg text-black h-12 w-56 font-bold text-lg"
			>
				Opslaan
			</button>
		</div>
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
