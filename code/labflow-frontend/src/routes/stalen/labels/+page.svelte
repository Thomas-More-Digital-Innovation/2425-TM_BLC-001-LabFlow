<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import LabelCart from "../../../components/LabelCart.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte'

    import { getUserId } from "$lib/globalFunctions";
	import Layout from "../../+layout.svelte";

</script>

<Nav/>
<main class="px-8">
    <div class="bg-gray-200 rounded-2xl h-full p-4">
        <!-- top section -->
         <div class="flex flex-row space-x-4">
            <!-- Invullen patientgegevens -->
            <div class="grid grid-cols-5 bg-white rounded-lg h-20 w-5/6 space-x-2  px-2">
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Code</p>
                    <p class="font-bold">Nieuw<!--{nieuweStaalCode || "loading..."}--></p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Achternaam</p>
                    <p class="font-bold">Van Leuffelen</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Voornaam</p>
                    <p class="font-bold">César</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geboortedatum</p>
                    <p class="font-bold">29/07/2004</p>
                </div>
                <div class="flex flex-col justify-center pl-5">
                    <p class="text-gray-400">Geslacht</p>
                    <p class="font-bold">Man</p>
                </div>
            </div>


            <!-- navigatie volgende en terug -->
            <div class="pb-5 flex flex-row space-x-2 justify-end w-3/12">
                <button on:click={() => { goto("/stalen") }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    <div class="w-5 h-5 mr-2"><FaArrowLeft/></div>
                    Terug
                </button>
                <!-- staat tijdelijk naar volgende pagina omdat ik nog niet weet hoe César zijn pagina heet -->
                <button on:click={() => { goto("/stalen") }} class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    Volgende
                    <div class="w-5 h-5 ml-2"><FaArrowRight/></div>
                </button>
            </div>
        </div>
        <!-- bottom section -->
         <div class="flex space-x-4 h-full">
            <!-- left section -->
            <div class="bg-white w-1/3 h-[75vh] rounded-xl p-4">
                <p class="text-blue-500">5 labels</p>
                <!-- standaard label -->
                <div class=" border border-gray-200 rounded-xl flex justify-between items-center p-4 my-3">
                    <p class="font-bold text-lg">César Van Leuffelen</p>
                    <p class="py-3 px-8 rounded-full text-white bg-blue-500">standaard</p>
                </div>
                <!-- loop labels -->
                <LabelCart labelName="Heparine" color="bg-green-500" />
            </div>
             <!-- right section -->
            <div class="w-2/3 flex flex-col justify-between space-y-4">
                <!-- pdf previewer -->
                <div class="w-full h-4/5 bg-gray-400">
                    <iframe src="https://s28.q4cdn.com/392171258/files/doc_downloads/test.pdf" title="pdf label preview" width="100%" class="h-full" />
                </div>
                <!-- bedienings knoppen -->
                 <div class="w-full h-1/5 bg-slate-200 flex justify-between items-baseline">
                    <!-- left button-->
                    <div class="w-1/4 mt-auto">
                        <button class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-full flex flex-row items-center justify-center">
                            afdrukken
                        </button>
                    </div>
                    <!-- right selects -->
                    <div class=" w-2/4 mt-auto flex justify-between items-center">
                        <div class="w-1/2">
                            <p>hoeveelheid</p>
                            <input type="number" class="rounded-lg text-xl p-3 h-20 w-11/12 bg-white border border-gray-400">
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