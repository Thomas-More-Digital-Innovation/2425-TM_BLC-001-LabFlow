<script lang="ts">
	import Nav from '../../../components/nav.svelte';
	import LabelCart from './../../../components/LabelCart.svelte';
	import { goto } from '$app/navigation';
	import { getCookie, fetchAll, formatDate, formatSex } from '$lib/globalFunctions';
	import { getRol } from '$lib/globalFunctions';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';
	// @ts-ignore
	import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte';
	import { staalCodeStore } from '$lib/store';
	import { onMount } from 'svelte';

	// neem de id
	let sampleCode: string | undefined;
	staalCodeStore.subscribe((value) => {
		sampleCode = value;
		console.log('Dit is staalcode:' + sampleCode);
	});

	let tests: any[] = [];
	let staal: any = {};
	let staalId: String = '';
	let testCategories: any[] = [];
	const token = getCookie('authToken') || '';

	// printen
	let hoeveelheid: number = 1;

	// alle tests categorieën ophalen die bij de testen horen
	async function loadData() {
		if (token != null) {
			try {
				staal = await fetchAll(token, `staal/${sampleCode}`);
				// assign id to staalId
				staalId = staal.id;
				console.log(staalId);
				// Extract unique test categories
				extractUniqueTestCategories(staal.registeredTests);
			} catch (error) {
				console.error('data kon niet gefetched worden:', error);
			}
		} else {
			console.error('jwt error');
			goto('/login');
		}
	}

	// Get unique test categories based on their id
	function extractUniqueTestCategories(registeredTests: any[]) {
		const categoryMap = new Map();

		registeredTests.forEach((testItem) => {
			const category = testItem.test.testcategorie;
			if (!categoryMap.has(category.id)) {
				categoryMap.set(category.id, category);
			}
		});

		// Convert Map to an array of unique categories
		testCategories = Array.from(categoryMap.values());
		console.log('Unique Test Categories:', testCategories);
	}

	// load the pdf
	let pdfSrc = '';

	async function loadPdf() {
		try {
			const response = await fetch(`http://localhost:8080/api/pdf/generatelabel/${staalId}`, {
				method: 'GET', // Change to POST if necessar
				headers: {
					'Content-Type': 'application/pdf' // Ensure correct content-type
				}
			});

			if (!response.ok) {
				throw new Error('Failed to fetch the PDF');
			}

			// Convert response to blob
			const pdfBlob = await response.blob();

			// Create a URL for the blob and set it as the iframe source
			pdfSrc = URL.createObjectURL(pdfBlob);
			console.log('PDF URL:', pdfSrc);
		} catch (error) {
			console.error('Error fetching PDF:', error);
		}
	}

	onMount(() => {
		loadData();
	});
</script>

<Nav />
<main class="px-8">
	<div class="bg-gray-200 rounded-2xl h-full p-4">
		<!-- top section -->
		<div class="flex flex-row space-x-4">
			<!-- Invullen patientgegevens -->
			<div class="grid grid-cols-5 bg-white rounded-lg h-20 w-5/6 space-x-2 px-2">
				<div class="flex flex-col justify-center">
					<p class="text-gray-400">Code</p>
					<p class="font-bold">{sampleCode || 'loading...'}</p>
				</div>
				<div class="flex flex-col justify-center">
					<p class="text-gray-400">Achternaam</p>
					<p class="font-bold">{staal?.patientAchternaam || 'loading...'}</p>
				</div>
				<div class="flex flex-col justify-center">
					<p class="text-gray-400">Voornaam</p>
					<p class="font-bold">{staal?.patientVoornaam || 'loading...'}</p>
				</div>
				<div class="flex flex-col justify-center">
					<p class="text-gray-400">Geboortedatum</p>
					<p class="font-bold">
						{staal.patientGeboorteDatum ? formatDate(staal.patientGeboorteDatum) : 'loading...'}
					</p>
				</div>
				<div class="flex flex-col justify-center pl-5">
					<p class="text-gray-400">Geslacht</p>
					<p class="font-bold">
						{staal.patientGeslacht ? formatSex(staal.patientGeslacht) : 'loading...'}
					</p>
				</div>
			</div>

			<!-- navigatie volgende en terug -->
			<div class="pb-5 flex flex-row space-x-2 justify-end w-3/12">
				<button
					on:click={() => {
						goto('/stalen');
					}}
					class="bg-gray-400 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center"
				>
					<div class="w-5 h-5 mr-2"><FaArrowLeft /></div>
					Annuleren
				</button>
				<!-- staat tijdelijk naar volgende pagina omdat ik nog niet weet hoe César zijn pagina heet -->
				<button
					on:click={() => {
						goto('/stalen/saved');
					}}
					class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center"
				>
					Volgende
					<div class="w-5 h-5 ml-2"><FaArrowRight /></div>
				</button>
			</div>
		</div>
		<!-- bottom section -->
		<div class="flex space-x-4 h-full">
			<!-- left section -->
			<div class="bg-white w-1/3 h-[75vh] rounded-xl p-4">
				<p class="text-blue-500">{testCategories.length + 1} labels</p>
				<!-- standaard label -->
				<div class=" border border-gray-200 rounded-xl flex justify-between items-center p-4 my-3">
					<p class="font-bold text-lg">César Van Leuffelen</p>
					<p class="py-3 px-8 rounded-full text-white bg-blue-500">standaard</p>
				</div>
				<!-- loop labels -->
				{#each testCategories as testcategorie}
					<LabelCart labelName={testcategorie.naam} color={testcategorie.kleur} />
				{/each}
			</div>
			<!-- right section -->
			<div class="w-2/3 flex flex-col justify-between space-y-4">
				<!-- pdf previewer -->
				<div class="w-full h-4/5">
					<iframe
						src="http://localhost:8080/files/label_Maes_Victor.pdf"
						title="pdf label preview"
						width="100%"
						class="h-full rounded-xl"
					/>
				</div>
				<!-- bedienings knoppen -->
				<div class="w-full h-1/5 bg-slate-200 flex justify-between items-baseline">
					<!-- left button-->
					<div class="w-1/4 mt-auto">
						<button
							class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-full flex flex-row items-center justify-center"
						>
							afdrukken
						</button>
					</div>
					<!-- right selects -->
					<div class=" w-2/4 mt-auto flex justify-between items-center">
						<div class="w-1/2">
							<p>hoeveelheid</p>
							<input
								type="number"
								bind:value={hoeveelheid}
								class="rounded-lg text-xl p-3 h-20 w-11/12 bg-white border border-gray-400"
							/>
						</div>
						<div class="w-1/2">
							<p>printer</p>
							<select class="rounded-lg text-xl p-3 h-20 bg-white w-full border border-gray-400">
								<option value="standaard">- selecteer -</option>
								<option value="heparine">printer lokaal A</option>
								<option value="heparine">printer lokaal B</option>
							</select>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
