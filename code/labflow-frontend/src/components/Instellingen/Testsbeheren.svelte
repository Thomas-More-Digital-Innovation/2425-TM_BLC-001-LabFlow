<script lang="ts">
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import { fetchTests } from '$lib/fetchFunctions';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	// @ts-ignore
	import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte';
	import { getCookie } from '$lib/globalFunctions';

	const token = getCookie('authToken') || '';

	let errorMessageTest = '';
	let tests: any[] = [];

	onMount(async () => {
		const fetchedTests = await fetchTests();
		if (fetchedTests) {
			tests = fetchedTests;
		}
	});

	let testCode = '';
	let naam = '';
	let eenheid = '';
	let testcategorie = '';
	let referentiewaardes = '';

	///// DELETE test /////
	async function deleteTest(id: string) {
		console.log(id);
		try {
			await fetch(`http://localhost:8080/api/deletetest/${id}`, {
				method: 'DELETE',
				headers: {
					Authorization: 'Bearer ' + token
				}
			});
		} catch (error) {
			console.error('Test kon niet worden verwijderd: ', error);
		}
		const result = await fetchTests();
		if (result) {
			tests = result;
		}
	}

	///// PUT test /////
	let errorVeldenTestPUT = {
		testCode: false,
		naam: false,
		eenheid: false,
		testcategorie: false,
		referentiewaardes: false
	};

	let errorMessageStaalPUT = '';

	async function updateTest(id: string) {
		const test = tests.find((t) => t.id === id);
		if (!test) return;

		let isValid = true;

		let errorVeldenTestPUT = {
			testCode: false,
			naam: false,
			eenheid: false,
			testcategorie: false,
			referentiewaardes: false
		};

		if (!test.testCode) {
			errorVeldenTestPUT.testCode = true;
			isValid = false;
		}
		if (!test.naam) {
			errorVeldenTestPUT.naam = true;
		}
		if (!test.eenheid) {
			errorVeldenTestPUT.eenheid = true;
			isValid = false;
		}
		if (!test.testcategorie) {
			errorVeldenTestPUT.testcategorie = true;
			isValid = false;
		}
		if (!test.referentiewaardes) {
			errorVeldenTestPUT.referentiewaardes = true;
			isValid = false;
		}
		if (!isValid) {
			errorMessageStaalPUT = 'Vul alle verplichte velden in.';
			return;
		}
		console.log(test);
		try {
			await fetch(`http://localhost:8080/api/updatetest/${id}`, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					testCode: test.testCode,
					naam: test.naam,
					eenheid: test.eenheid,
					testcategorie: {
						id: test.testcategorie.id
					},
					referentiewaardes: test.referentiewaardes.map((waarde: any) => ({
						id: waarde.id
					}))
				})
			});
			errorMessageStaalPUT = '';
		} catch (error) {
			console.error('Test kon niet worden aangepast: ', error);
		}
		return;
	}
</script>

<div class="flex flex-col w-full ml-5">
	<div class="flex flex-row justify-between w-full h-14 mb-5">
		<h1 class="font-bold text-3xl">Testen beheren</h1>
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
				class="grid grid-cols-10 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold space-x-3"
			>
				<div class="col-span-1 text-left">
					<p>Testcode</p>
				</div>
				<div class="col-span-2 text-left">
					<p>Naam</p>
				</div>
				<div class="col-span-2 text-left">
					<p>Categorie</p>
				</div>
				<div class="col-span-2 text-left">
					<p>Eenheid</p>
				</div>
				<div class="col-span-2 text-left">
					<p>Referentiewaardes</p>
				</div>
				<div class="col-span-1 text-right">
					<p>Acties</p>
				</div>
			</div>
			{#if errorMessageTest}
				<div class="text-red-500 mb-2">{errorMessageTest}</div>
			{/if}
			<div class="space-y-3">
				{#each tests as test, index}
					<div
						class="grid grid-cols-10 bg-white rounded-lg h-20 items-center px-3 shadow-md space-x-3"
					>
						<div class="col-span-1">
							<input
								type="text"
								on:blur={() => updateTest(test.id)}
								id="test-{test?.id}"
								bind:value={test.testCode}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-2">
							<input
								type="text"
								on:blur={() => updateTest(test.id)}
								id="test-{test?.id}"
								bind:value={test.naam}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-2">
							<input
								type="text"
								on:blur={() => updateTest(test.id)}
								bind:value={test.eenheid}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-2">
							<input
								type="text"
								on:blur={() => updateTest(test.id)}
								bind:value={test.testcategorie}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-2">
							<input
								type="text"
								on:blur={() => updateTest(test.id)}
								bind:value={test.referentiewaardes}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>

						<!-- Acties -->
						<div class="col-span-1 flex justify-end">
							{#if test.confirmDelete}
								<button
									type="button"
									on:click={() => deleteTest(test?.id)}
									class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white"
								>
									<FaTrashAlt />
								</button>
							{:else}
								<button
									type="button"
									on:click={() => {
										tests.forEach((c, i) => {
											if (i !== index) c.confirmDelete = false;
										});
										test.confirmDelete = true;
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
</div>
