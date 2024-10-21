<script>
    import { goto } from "$app/navigation";
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
</script>

<div class="flex flex-col w-full ml-5">
    <div class="flex flex-row justify-between w-full h-14 mb-5">
        <h1 class="font-bold text-3xl">Gebruikers beheren</h1>
        <button type="button" on:click={async () => { await goto("/stalen"); }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex">
            <div class="w-4 h-4 mr-2"><FaArrowLeft/></div>
            <p>Terug</p>
        </button>
    </div>

    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        test
    </div>
</div>