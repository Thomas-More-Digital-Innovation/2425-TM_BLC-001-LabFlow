<script lang="ts">
    import Nav from "../../components/nav.svelte";
    // @ts-ignore
    import GoPlus from 'svelte-icons/go/GoPlus.svelte';
    // @ts-ignore
    import IoMdSettings from 'svelte-icons/io/IoMdSettings.svelte'
    import { getRol } from '$lib/globalFunctions';
	import Staal from "../../components/Staal.svelte";


    let bgColor = 'bg-blue-400';
    let pointerEvent = 'pointer-events-auto';
    const rol = getRol();
    if (rol !== 'admin') {
        bgColor = 'bg-gray-400';
        pointerEvent = 'pointer-events-none';
    }

</script>

<Nav />
<div class="px-8 flex flex-row space-x-5">
    <div class="flex flex-col space-y-5">
        <a href='/stalen/nieuw' class="bg-blue-400 flex flex-col items-center justify-center w-72 h-72 rounded-2xl">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <GoPlus />
            </div>
            <p class="text-white text-2xl text-center mt-2">Nieuwe staal</p>
        </a>

        <a href={rol === 'admin' ? '/instellingen' : '#'}
        class="{bgColor} flex flex-col items-center justify-center w-72 h-72 rounded-2xl {pointerEvent}">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <IoMdSettings />
            </div>
            <p class="text-white text-2xl text-center mt-2">Instellingen</p>
        </a>
    </div>
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        <form action="" class="flex space-x-5 mb-5">
            <input type="text" id="search" name="search" placeholder="zoeken" class="w-full h-12 rounded-lg text-black pl-3">
            <input type="date" id="date" name="date" class="w-full h-12 rounded-lg text-black px-3">
        </form>
        <Staal />
    </div>
</div>
