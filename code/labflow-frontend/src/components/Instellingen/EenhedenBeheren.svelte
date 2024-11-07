<script lang="ts">
	import { goto } from '$app/navigation';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';
	import { fetchEenheden } from '$lib/fetchFunctions';
	import { onMount } from 'svelte';
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	// @ts-ignore
	import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte';
	// @ts-ignore
	import FaPlus from 'svelte-icons/fa/FaPlus.svelte';
	import { getCookie } from '$lib/globalFunctions';

	const token = getCookie('authToken') || '';

	let errorMessageEenheid = '';
	let eenheden: any[] = [];

	onMount(async () => {
		const fetchedEenheden = await fetchEenheden();
		if (fetchedEenheden) {
			eenheden = fetchedEenheden;
		}
		console.log(eenheden);
	});

	///// DELETE eenheid /////
	async function deleteEenheid(id: string) {
		console.log(id);
		try {
			await fetch(`http://localhost:8080/api/deleteeenheid/${id}`, {
				method: 'DELETE',
				headers: {
					Authorization: 'Bearer ' + token
				}
			});
		} catch (error) {
			console.error('Eenheid kon niet worden verwijderd: ', error);
		}
		const result = await fetchEenheden();
		if (result) {
			eenheden = result;
		}
	}

	///// PUT eenheid /////
	let errorVeldenEenheidPUT = {
		naam: false,
		afkorting: false
	};

	let errorMessageEenheidPUT = '';

	async function updateEenheid(id: string) {
		const eenheid = eenheden.find((e) => e.id === id);
		if (!eenheid) return;

		let isValid = true;

		errorVeldenEenheidPUT = {
			naam: false,
			afkorting: false
		};

		if (!eenheid.naam) {
			errorVeldenEenheidPUT.naam = true;
			isValid = false;
		}
		if (!eenheid.afkorting) {
			errorVeldenEenheidPUT.afkorting = true;
		}
		if (!isValid) {
			errorMessageEenheidPUT = 'Vul alle verplichte velden in.';
			return;
		}
		console.log(eenheid);
		try {
			await fetch(`http://localhost:8080/api/updateeenheid/${id}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					naam: eenheid.naam,
					afkorting: eenheid.afkorting
				})
			});
			errorMessageEenheidPUT = '';
		} catch (error) {
			console.error('Eenheid kon niet worden aangepast: ', error);
		}
		return;
	}
</script>

<div class="flex flex-col w-full ml-5">
	<div class="flex flex-row justify-between w-full h-14 mb-5">
		<h1 class="font-bold text-3xl">Eenheden beheren</h1>
		<button
			type="button"
			on:click={async () => {
				await goto('/stalen');
			}}
			class="bg-gray-400 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex"
		>
			<div class="w-4 h-4 mr-2"><FaArrowLeft /></div>
			<p>Terug</p>
		</button>
	</div>

	<div class="bg-slate-200 w-full h-full rounded-2xl p-5">
		<div class="space-y-3">
			<!-- Header -->
			<div
				class="grid grid-cols-7 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold space-x-3"
			>
				<div class="col-span-3 text-left">
					<p>Afkorting</p>
				</div>
				<div class="col-span-3 text-left">
					<p>Naam</p>
				</div>
				<div class="col-span-1 text-right">
					<p>Acties</p>
				</div>
			</div>
			{#if errorMessageEenheid}
				<div class="text-red-500 mb-2">{errorMessageEenheid}</div>
			{/if}
		</div>

		<div class="space-y-3">
			{#each eenheden as eenheid, index}
				<div
					class="grid grid-cols-7 bg-white rounded-lg h-20 items-center px-3 shadow-md space-x-3"
				>
					<div class="col-span-3">
						<input
							type="text"
							on:blur={() => updateEenheid(eenheid.id)}
							id="eenheid-{eenheid?.id}"
							bind:value={eenheid.afkorting}
							class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
						/>
					</div>
					<div class="col-span-3">
						<input
							type="text"
							on:blur={() => updateEenheid(eenheid.id)}
							id="eenheid-{eenheid?.id}"
							bind:value={eenheid.naam}
							class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
						/>
					</div>

					<!-- Acties -->
					<div class="col-span-1 flex justify-end">
						{#if eenheid.confirmDelete}
							<button
								type="button"
								on:click={() => deleteEenheid(eenheid?.id)}
								class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white"
							>
								<FaTrashAlt />
							</button>
						{:else}
							<button
								type="button"
								on:click={() => {
									eenheden.forEach((c, i) => {
										if (i !== index) c.confirmDelete = false;
									});
									eenheid.confirmDelete = true;
								}}
								class="h-10 w-10 bg-red-300 p-2 rounded-lg text-white"
							>
								<GoX />
							</button>
						{/if}
					</div>
				</div>
			{/each}
		</div>
	</div>
</div>
