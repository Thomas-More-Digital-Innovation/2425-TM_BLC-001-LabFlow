<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import LabelCart from "./../../../components/LabelCart.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll, formatDate, formatSex } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte'
    // @ts-ignore
    import FaCheck from 'svelte-icons/fa/FaCheck.svelte'
    // @ts-ignore
    import IoIosClose from 'svelte-icons/io/IoIosClose.svelte'
    // @ts-ignore
    import IoMdText from 'svelte-icons/io/IoMdText.svelte'

    import { staalCodeStore } from '$lib/store';
	import { onMount } from "svelte";
    import { slide } from 'svelte/transition';

    // neem de id
    let sampleCode: string | undefined;
    staalCodeStore.subscribe(value => {
        sampleCode = value;
        console.log("Dit is staalcode:" + sampleCode);
    });

    let tests: any[] = [];
    let staal: any = {};
    let staalId: String = '';
    let testCategories: any[] = [];
    const token = getCookie('authToken') || '';

    // alle tests categorieën ophalen die bij de testen horen
    async function loadData() {
        if (token != null) {
            try {
                staal = await fetchAll(token, `staal/${sampleCode}`);
                // assign id to staalId
                staalId = staal.id
                console.log(staalId)
                // Extract unique test categories
                extractUniqueTestCategories(staal.registeredTests);
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    // Get unique test categories based on their id
    function extractUniqueTestCategories(registeredTests: any[]) {
        const categoryMap = new Map();

        registeredTests.forEach(testItem => {
            const category = testItem.test.testcategorie;
            if (!categoryMap.has(category.id)) {
                categoryMap.set(category.id, category);
            }
        });

        // Convert Map to an array of unique categories
        testCategories = Array.from(categoryMap.values());
        console.log("Unique Test Categories:", testCategories);
    }

    // function for showing the notes
    let showNoteInput = false; // Track the visibility of the note input field

    function toggleNoteInput() {
        showNoteInput = !showNoteInput; // Toggle the note input field visibility
    }

    onMount(() => {
        loadData();
    });
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
                    <p class="font-bold">{sampleCode || "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Achternaam</p>
                    <p class="font-bold">{staal?.patientAchternaam || "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Voornaam</p>
                    <p class="font-bold">{staal?.patientVoornaam || "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geboortedatum</p>
                    <p class="font-bold">{staal.patientGeboorteDatum ? formatDate(staal.patientGeboorteDatum) : "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center pl-5">
                    <p class="text-gray-400">Geslacht</p>
                    <p class="font-bold">{staal.patientGeslacht ? formatSex(staal.patientGeslacht) : "loading..."}</p>
                </div>
            </div>


            <!-- navigatie volgende en terug -->
            <div class="pb-5 flex flex-row space-x-2 justify-end w-3/12">
                <button on:click={() => { goto("/stalen") }} class="bg-gray-400 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    <div class="w-5 h-5 mr-2"><FaArrowLeft/></div>
                    Annuleren
                </button>
                <!-- staat tijdelijk naar volgende pagina omdat ik nog niet weet hoe César zijn pagina heet -->
                <button on:click={() => { goto("/stalen/done") }} class="bg-blue-600 text-xl rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    Volgende
                    <div class="w-5 h-5 ml-2"><FaArrowRight/></div>
                </button>
            </div>
        </div>
        <!-- bottom section -->
         <div class="flex space-x-4 h-full">
            <!-- left section -->
            <div class="bg-white w-1/3 h-[75vh] rounded-xl p-4">
                <p class="text-blue-500">3 labels</p>
                <!-- check labels -->
                <div class=" border border-gray-200 rounded-xl flex justify-between items-center p-4 my-3 hover:bg-gray-100 hover:scale-[101%] transition">
                    <p class="font-bold text-lg">Done</p>
                    <div class={`p-3 rounded-full text-white h-12`} style={`background-color: #23E22C;`}><FaCheck /></div>
                </div>
                <!-- not check labels -->
                <div class=" border border-gray-200 rounded-xl flex justify-between items-center p-4 my-3 hover:bg-gray-100 hover:scale-[101%] transition">
                    <p class="font-bold text-lg">Not Done</p>
                    <div class={`p-1 rounded-full text-white h-12`} style={`background-color: #E3E3E3;`}><IoIosClose/></div>
                </div>
            </div>
             <!-- right section -->
            <div class="w-2/3 flex flex-col justify-between space-y-4">
                <!-- white box -->
                <div class="w-full h-full bg-white rounded-xl">
                    <!-- header-->
                    <div class="h-1/6 flex justify-between mx-6">
                        <!-- left -->
                        <div class="flex justify-start items-center">
                            <div class={`p-8 rounded-full text-white`} style={`background-color: #000;`}></div>
                            <h2 class="ml-8 font-bold text-lg">Serum</h2>
                        </div>
                        <!-- right (button) -->
                         <div class="flex justify-end items-center">
                            <button class="bg-blue-600 text-xl rounded-lg p-3 text-white h-1/2 w-full flex flex-row items-center justify-center">Volgende</button>
                         </div>
                    </div>
                    <!-- List-->
                     <div class="h-5/6 mx-6">
                        <div class="w-full p-4 bg-white border border-gray-200 rounded-xl">
                            <div class="w-full p-4 flex items-center justify-between">
                                <!-- Code Section -->
                                <div class="flex flex-col items-start">
                                    <span class="text-sm text-gray-500">Code</span>
                                    <span class="text-xl font-semibold">121</span>
                                </div>

                                <!-- Test Section -->
                                <div class="flex flex-col items-start">
                                    <span class="text-sm text-gray-500">Test</span>
                                    <span class="text-xl font-semibold">HDL-Cholestrol</span>
                                </div>

                                <!-- Unit Section -->
                                <div class="flex flex-col items-start">
                                    <span class="text-sm text-gray-500">Waarde</span>
                                    <input type="text" class="bg-gray-200 h-10 rounded-lg border border-gray-400 px-1"/>
                                </div>

                                <!-- Unit Section -->
                                <div class="flex flex-col items-start">
                                    <span class="text-sm text-gray-500">Eenheid</span>
                                    <span class="text-xl font-semibold">mg/dL</span>
                                </div>

                                <!-- Checkbox Section -->
                                <div class="flex flex-col items-center">
                                    <span class="text-sm text-gray-500">Gefaald</span>
                                    <input type="checkbox" class="w-5 h-5 mt-3 text-blue-500 border-gray-300 rounded focus:ring-2 focus:ring-blue-500">
                                </div>

                                <!-- nota Section -->
                                <div>
                                    <div class="flex flex-col items-center">
                                        <span class="text-sm text-gray-500">Nota</span>
                                        <button on:click={toggleNoteInput} class="text-white bg-blue-500 h-12 p-3 rounded-lg">
                                            <IoMdText />
                                        </button>
                                    </div>
                                </div>
                            </div>
                            {#if showNoteInput}
                                <div>
                                    <div transition:slide class="mt-4 p-4">
                                        <span class="text-sm text-gray-500">Nota</span>
                                        <textarea 
                                            class="w-full h-20 p-2 rounded-lg border bg-gray-200 border-gray-400 resize-none" 
                                            placeholder="Voeg een nota toe...">
                                        </textarea>
                                    </div>
                                </div>
                            {/if}
                        </div>
                        
                    </div>
                </div>
            </div>
         </div>
    </div>
</main>

