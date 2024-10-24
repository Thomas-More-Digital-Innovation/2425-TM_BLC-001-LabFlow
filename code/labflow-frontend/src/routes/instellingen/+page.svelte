<script>
	import Nav from '../../components/nav.svelte';
	// @ts-ignore
	import FaClipboardList from 'svelte-icons/fa/FaClipboardList.svelte';
	// @ts-ignore
	import GiTestTubes from 'svelte-icons/gi/GiTestTubes.svelte';
	// @ts-ignore
	import FaUserAlt from 'svelte-icons/fa/FaUserAlt.svelte';
	// @ts-ignore
	import GiHypodermicTest from 'svelte-icons/gi/GiHypodermicTest.svelte';
	import CategorieënBeheren from '../../components/Instellingen/CategorieënBeheren.svelte';
	import GebruikersBeheren from '../../components/Instellingen/GebruikersBeheren.svelte';
	import StalenBeheren from '../../components/Instellingen/StalenBeheren.svelte';
	import TestsBeheren from '../../components/Instellingen/Testsbeheren.svelte';

	// default staat nu op testen beheren
	let navElement = 'Gebruiker';
</script>

<Nav />
<div class="px-8 flex flex-row">
	<!-- 4 knoppen -->
	<div class="flex flex-col space-y-3 w-40 mt-[74px]">
		<button
			type="button"
			on:click={() => (navElement = 'Categorie')}
			class="p-3 text-center text-lg {navElement == 'Categorie'
				? 'bg-blue-500'
				: 'bg-blue-400'} flex flex-col items-center justify-center w-40 h-40 rounded-2xl text-white"
		>
			<div class="w-14 h-14 mb-5"><FaClipboardList /></div>
			Categorieën beheren
		</button>
		<button
			type="button"
			on:click={() => (navElement = 'Staal')}
			class="p-3 text-center text-lg {navElement == 'Staal'
				? 'bg-blue-500'
				: 'bg-blue-400'} flex flex-col items-center justify-center w-40 h-40 rounded-2xl text-white"
		>
			<div class="w-14 h-14 mb-5"><GiTestTubes /></div>
			Stalen beheren
		</button>
		<button
			type="button"
			on:click={() => (navElement = 'Test')}
			class="p-3 text-center text-lg {navElement == 'Test'
				? 'bg-blue-500'
				: 'bg-blue-400'} flex flex-col items-center justify-center w-40 h-40 rounded-2xl text-white"
		>
			<div class="w-14 h-14 mb-5"><GiHypodermicTest /></div>
			Tests beheren
		</button>
		<button
			type="button"
			on:click={() => (navElement = 'Gebruiker')}
			class="p-3 text-center text-lg {navElement == 'Gebruiker'
				? 'bg-blue-500'
				: 'bg-blue-400'} flex flex-col items-center justify-center w-40 h-40 rounded-2xl text-white"
		>
			<div class="w-14 h-14 mb-5"><FaUserAlt /></div>
			Gebruikers beheren
		</button>
	</div>

	<!-- content -->
	{#if navElement === 'Categorie'}
		<CategorieënBeheren />
	{:else if navElement === 'Staal'}
		<StalenBeheren />
	{:else if navElement === 'Gebruiker'}
		<GebruikersBeheren />
	{:else if navElement === 'Test'}
		<TestsBeheren />
	{/if}
</div>
