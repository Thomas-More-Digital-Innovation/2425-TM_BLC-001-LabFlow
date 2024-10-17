<script lang="ts">
    import Nav from "../../../components/nav.svelte";
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';
    import { getRol } from '$lib/globalFunctions';
    // @ts-ignore
    import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte'
    // @ts-ignore
    import FaRegEdit from 'svelte-icons/fa/FaRegEdit.svelte'
    // @ts-ignore
    import FaPlus from 'svelte-icons/fa/FaPlus.svelte'
    // @ts-ignore
    import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte'
    // @ts-ignore
    import FaArrowRight from 'svelte-icons/fa/FaArrowRight.svelte'

    // voor het inladen van crud voor admins
    const rol = getRol();

    let tests: any[] = [];
    let filteredTests: any[] = [];
    let searchCode = '';
    const token = getCookie('authToken') || '';
    let nieuweStaalCode = '';

    // fetchen van tests op "tests"
    // verkrijgen nieuwe staalcode op "/api/newStaalCode"
    async function loadData() {
        if (token != null) {
            try {
                tests = await fetchAll(token, 'tests');
                filteredTests = tests; // zonder filter worden alle tests ingeladen
                nieuweStaalCode = await fetchAll(token, 'newStaalCode'); // fetchen van nieuwe staalcode
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    loadData();

    // zoeken op basis van code
    function filterTests() {
        filteredTests = tests.filter(test => {
            const codeMatch = test.testCode.toString().toLowerCase().includes(searchCode.toLowerCase());
            return codeMatch;
        });
    }

    function verwijderSelectie() {
        searchCode = '';
        filterTests();
    }
</script>

<Nav/>
<div class="px-8">
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">

        <h1 class="font-bold text-xl mb-2">Patiëntgegevens</h1>
        <div class="flex flex-row space-x-4">
            <!-- Invullen patientgegevens -->
            <div class="grid grid-cols-5 bg-white rounded-lg h-20 w-5/6 space-x-2  px-2">
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Code</p>
                    <p class="font-bold">{nieuweStaalCode || "loading..."}</p>
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Naam</p>
                    <input 
                    type="text"
                    id="searchCode"
                    name="searchCode"
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Voornaam</p>
                    <input 
                    type="text"
                    id="searchCode"
                    name="searchCode"
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geslacht</p>
                    <input 
                    type="text"
                    id="searchCode"
                    name="searchCode"
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3">
                </div>
                <div class="flex flex-col justify-center">
                    <p class="text-gray-400">Geboortedatum</p>
                    <input 
                    type="text"
                    id="searchCode"
                    name="searchCode"
                    class="rounded-lg text-black bg-gray-200 h-10 pl-3">
                </div>
            </div>


            <!-- navigatie volgende en terug -->
            <div class="pb-5 flex flex-row space-x-2 justify-end w-1/6">
                <button on:click={() => { goto("/stalen") }} class="bg-gray-400 rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    <div class="w-3 h-3 mr-2"><FaArrowLeft/></div>
                    Terug
                </button>
                <button class="bg-blue-600 rounded-lg p-3 text-white h-20 w-1/2 flex flex-row items-center justify-center">
                    Volgende
                    <div class="w-3 h-3 ml-2"><FaArrowRight/></div>
                </button>
            </div>
        </div>
        

        <div class="rounded-xl bg-white">
            <div class="bg-white rounded-xl p-3 flex flex-row items-center">
                <!-- zoeken op code -->
                <input
                    type="text" 
                    id="searchCode" 
                    name="searchCode" 
                    placeholder="zoeken op code"
                    bind:value={searchCode} on:input={filterTests}
                    class="w-1/5 h-12 rounded-lg text-black pl-3 bg-gray-200">

                <!-- verwijder selectie -->
                <button on:click={verwijderSelectie} class="bg-red-200 rounded-lg p-3 text-black h-12 w-48 ml-4">Verwijder selectie</button>

                <!-- dynamisch tonen hoeveel geselecteerde tests -->
                <p class="ml-6 pl-5 border-l-2 text-blue-600"><span>0</span> geselecteerd</p>
                
                <!-- knoppen voor aanmaken cat & test -->
                <div class="ml-auto flex flex-row justify-end space-x-2 w-2/5">
                    {#if rol === 'admin'}
                    <button class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                        <div class="w-3 h-3 mr-2"><FaPlus/></div>
                        Categorie aanmaken
                    </button>
                    <button class="bg-gray-200 rounded-lg p-3 text-black h-12 flex flex-row items-center justify-center flex-grow">
                        <div class="w-3 h-3 mr-2"><FaPlus/></div>
                        Test aanmaken
                    </button>
                    {/if}
                </div>
            </div>
            
            <!-- tabel met tests -->
            {#each filteredTests as test}
            <div class="grid grid-cols-12 gap-4 h-16 items-center px-3 border-b border-gray-300">
                <div class="col-span-1">
                    <input 
                        type="checkbox" 
                        name="select" 
                        id="select" 
                        class="w-5 h-5 mt-2 appearance-none border-2 border-gray-300 rounded-md checked:bg-blue-600 checked:border-transparent focus:outline-none">
                </div>
                <div class="col-span-2">
                    <p class="text-gray-400">Code</p>
                    <p>{test?.testCode || 'Loading...'}</p>
                </div>
                <div class="col-span-5">
                    <p class="text-gray-400">Naam</p>
                    <!-- truncate zorgt ervoor dat te lange namen eindigen met '...' -->
                    <p class="truncate">{test?.naam || 'Loading...'}</p>
                </div>
                <div class="col-span-3">
                    <p class="text-gray-400">Categorie</p>
                    <p>{test?.testcategorie.naam || 'Loading...'}</p>
                </div>
                {#if rol === 'admin'}
                <div class="col-span-1 flex justify-end space-x-2">
                    <div class="h-10 w-10 bg-blue-400 p-2 rounded-lg text-white">
                        <FaRegEdit />
                    </div>
                    <div class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white">
                        <FaTrashAlt />
                    </div>
                </div>
                {/if}
            </div>
            {/each}
        </div>
    </div>
</div>