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


    let tests: any[] = [];

    async function loadData() {
        const token = getCookie('authToken') || '';

        if (token != null) {
            try {
                tests = await fetchAll(token, 'tests');
                console.log(tests);
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    loadData();

    const rol = getRol();
</script>

<Nav/>

<div class="px-8">
    <div class="bg-slate-200 w-full h-full rounded-2xl p-5">
        <div class="w-full pb-5 flex flex-row space-x-2 justify-end">
            <button class="bg-gray-400 rounded-lg p-3 text-white h-12 w-1/6 flex flex-row items-center justify-center">
                <div class="w-3 h-3 mr-2"><FaArrowLeft/></div>
                Terug</button>
            <button class="bg-blue-600 rounded-lg p-3 text-white h-12 w-1/6 flex flex-row items-center justify-center">
                Volgende
                <div class="w-3 h-3 ml-2"><FaArrowRight/></div>
            </button>
        </div>

        <div class="rounded-xl bg-white">
            <div class="bg-white rounded-xl p-3 flex flex-row items-center">
                <!-- zoeken op code -->
                <input
                    type="text" 
                    id="searchCode" 
                    name="searchCode" 
                    placeholder="zoeken op code"
                    class="w-1/5 h-12 rounded-lg text-black pl-3 bg-gray-200">

                <!-- dynamisch tonen hoeveel geselecteerde tests -->
                <p class="ml-6 pl-5 border-l-2 text-blue-600"><span>1</span> geselecteerd</p>
                
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
            
            
            
            {#each tests as test}
            <div class="grid grid-cols-12 gap-4 h-16 items-center px-3 border-b border-gray-300">
                <div class="col-span-1">
                    <input type="checkbox" name="select" id="select">
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
