<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte'
    // @ts-ignore
    import FaSave from 'svelte-icons/fa/FaSave.svelte'
    import { goto } from '$app/navigation';
</script>

<Nav/>
<main class="flex flex-col justify-center items-center h-[85vh]">
    <!-- Big icon -->
    <div class="text-blue-500">
        <FaSave/>
    </div>
    <!-- text -->
    <h1 class=" text-8xl text-blue-500">Tijd om proeven te doen</h1>
    <p class="text-2xl text-gray-600">Je testen zijn geregistreerd. Je kan ze terugvinden op het beginscherm.</p>
    <!-- navigation buttons -->
    <div class="flex justify-center space-x-4 items-center my-6">
        <button on:click={() => { goto("/stalen") }} class="bg-gray-400 text-lg rounded-lg p-3 w-56 text-white h-14 flex flex-row items-center justify-center">
                    <div class="w-5 h-5 mr-2"><FaArrowLeft/></div>
                    Home
                </button>
                <!-- staat tijdelijk naar volgende pagina omdat ik nog niet weet hoe César zijn pagina heet -->
                <button on:click={() => { goto("/stalen") }} class="bg-blue-600 text-lg rounded-lg w-56 p-3 text-white h-14 flex flex-row items-center justify-center">
                    Waarde Registreren
                    <div class="w-5 h-5 ml-2"><FaArrowRight/></div>
                </button>
    </div>


</main>