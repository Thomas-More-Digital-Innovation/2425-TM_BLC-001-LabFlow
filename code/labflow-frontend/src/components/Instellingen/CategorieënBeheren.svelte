<script lang="ts">
	import { goto } from '$app/navigation';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	// @ts-ignore
	import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte';
	// @ts-ignore
	import FaPlus from 'svelte-icons/fa/FaPlus.svelte';
	import { loadTestCategorieën } from '../../lib/fetchFunctions';
	import { onMount } from 'svelte';
	import ColorPicker, { ChromeVariant } from 'svelte-awesome-color-picker';
	import { getCookie } from '../../lib/globalFunctions';

	const token = getCookie('authToken') || '';

	let categorieën: any[] = [];

	onMount(async () => {
		const result = await loadTestCategorieën();
		if (result) {
			categorieën = result;
		}
	});

	///// DELETE verwijderen van een categorie /////
	async function deleteCategorie(id: string) {
		console.log(id);
		try {
			await fetch(`http://localhost:8080/api/testcategorie/${id}`, {
				method: 'DELETE',
				headers: {
					Authorization: 'Bearer ' + token
				}
			});
		} catch (error) {
			console.error('Categorie kon niet worden verwijderd: ', error);
		}
		const result = await loadTestCategorieën();
		if (result) {
			categorieën = result;
		}
		return;
	}

	///// POST aanmaken van een categorie /////
	let categorienaam = '';
	// dit geeft een warning in console en geeft de kleur transparant weer (dit is wat ik wil)
	let hex = '';

	let errorVeldenCategoriePOST = {
		categorienaam: false,
		kleur: false
	};

	let errorMessageCategorie = '';
	async function nieuweCategorie() {
		errorVeldenCategoriePOST = { categorienaam: false, kleur: false };
		let isValid = true;
		const regex = /^#([0-9A-F]{3}){1,2}$/i;

		if (!categorienaam) {
			errorVeldenCategoriePOST.categorienaam = true;
			isValid = false;
		}
		if (!hex || !regex.test(hex)) {
			errorVeldenCategoriePOST.kleur = true;
			isValid = false;
		}
		// errorMessageStaal tonen indien niet alle velden zijn ingevuld
		if (!isValid) {
			errorMessageCategorie = 'Geef de categorie een naam en een kleur.';
			return;
		}

		try {
			const response = await fetch('http://localhost:8080/api/createtestcategorie', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					naam: categorienaam,
					kleur: hex
				})
			});
			categorienaam = '';
			hex = '';
			errorMessageCategorie = '';
			if (response.status === 409) {
				errorMessageCategorie = 'Deze categorie bestaat al.';
				console.log(errorMessageCategorie);
			}
		} catch (error) {
			console.error('categorie kon niet worden aangemaakt: ', error);
		}
		const result = await loadTestCategorieën();
		if (result) {
			categorieën = result;
		}
		return;
	}

	///// PUT aanpassen van een categorie /////

	let errorVeldenCategoriePUT = {
		categorienaam: false,
		kleur: false
	};

	let errorMessageCategoriePUT = '';

	async function updateCategorie(id: string) {
		const categorie = categorieën.find((c) => c.id === id);
		console.log(categorie);
		if (!categorie) return;

		errorVeldenCategoriePUT = { categorienaam: false, kleur: false };
		let isValid = true;

		if (!categorie.naam) {
			errorVeldenCategoriePUT.categorienaam = true;
			isValid = false;
		}
		if (!categorie.kleur) {
			errorVeldenCategoriePUT.kleur = true;
		}
		if (!isValid) {
			errorMessageCategoriePUT = 'Vul alle verplichte velden in.';
			return;
		}
		try {
			await fetch(`http://localhost:8080/api/testCategorieen/${id}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					naam: categorie.naam,
					kleur: categorie.kleur
				})
			});
			errorMessageCategoriePUT = '';
		} catch (error) {
			console.error('Categorie kon niet worden aangepast: ', error);
		}
		return;
	}
</script>

<div class="flex flex-col w-full ml-5 mb-10">	
	<div class="flex flex-row justify-between w-full h-14 mb-5">
		<h1 class="font-bold text-3xl">Categorieën beheren</h1>
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

	<div class="bg-slate-100 w-full h-full rounded-2xl p-5">
		<div class="space-y-3">
			<!-- Header -->
			<div class="grid grid-cols-12 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold">
				<div class="col-span-4">
					<p>Naam</p>
				</div>
				<div class="col-span-4 text-center">
					<p>Kleur</p>
				</div>
				<div class="col-span-4 text-right">
					<p>Acties</p>
				</div>
			</div>
			{#if errorMessageCategorie}
				<div class="text-red-500 mb-2">{errorMessageCategorie}</div>
			{/if}
			<div class="grid grid-cols-12 gap-4 bg-white rounded-lg h-20 items-center px-3 shadow-md">
				<!-- Naam -->
				<div class="col-span-4">
					<input
						type="text"
						id="nieuwecategorie"
						bind:value={categorienaam}
						class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full {errorVeldenCategoriePOST.categorienaam
							? 'border-2 border-red-500'
							: ''}"
					/>
				</div>

				<!-- Kleur Picker -->
				<div class="col-span-4 flex justify-center items-center">
					<div class={errorVeldenCategoriePOST.categorienaam ? 'border-b border-red-500' : ''}>
						<ColorPicker
							bind:hex
							label="Kies een kleur"
							components={ChromeVariant}
							sliderDirection="horizontal"
						/>
					</div>
				</div>

				<!-- Acties -->
				<div class="col-span-4 flex justify-end">
					<button
						type="submit"
						class="h-10 w-10 bg-green-500 p-2 rounded-lg text-white"
						on:click={nieuweCategorie}
						aria-label="Nieuwe categorie toevoegen"
					>
						<FaPlus />
					</button>
				</div>
			</div>

			{#if errorMessageCategoriePUT}
				<div class="text-red-500 mb-2">{errorMessageCategoriePUT}</div>
			{/if}

			{#each categorieën as categorie, index}
				<div class="grid grid-cols-12 gap-4 bg-white rounded-lg h-20 items-center px-3 shadow-md">
					<!-- Naam -->
					<div class="col-span-4">
						<input
							type="text"
							on:blur={() => updateCategorie(categorie.id)}
							id="categorie-{categorie?.id}"
							bind:value={categorie.naam}
							class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
						/>
					</div>

					<!-- Kleur Picker -->
					<div class="col-span-4 flex justify-center items-center">
						<div class="custom-color-picker">
							<ColorPicker
								label="Kies een kleur"
								components={ChromeVariant}
								sliderDirection="horizontal"
								bind:hex={categorie.kleur}
								on:input={() => updateCategorie(categorie.id)}
							/>
						</div>
					</div>

					<!-- Acties -->
					<div class="col-span-4 flex justify-end">
						{#if categorie.confirmDelete}
							<button
								type="button"
								on:click={() => deleteCategorie(categorie?.id)}
								class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white"
							>
								<FaTrashAlt />
							</button>
						{:else}
							<button
								type="button"
								on:click={() => {
									categorieën.forEach((c, i) => {
										if (i !== index) c.confirmDelete = false;
									});
									categorie.confirmDelete = true;
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
