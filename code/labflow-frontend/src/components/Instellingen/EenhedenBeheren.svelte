<script>
	import { goto } from '$app/navigation';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';

	let errorMessageTest = '';
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
				class="grid grid-cols-3 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold space-x-3"
			>
				<div class="col-span-1 text-left">
					<p>Afkorting</p>
				</div>
				<div class="col-span-1 text-left">
					<p>Naam</p>
				</div>
				<div class="col-span-1 text-right">
					<p>Acties</p>
				</div>
			</div>
			{#if errorMessageTest}
				<div class="text-red-500 mb-2">{errorMessageTest}</div>
			{/if}
		</div>
	</div>
</div>
