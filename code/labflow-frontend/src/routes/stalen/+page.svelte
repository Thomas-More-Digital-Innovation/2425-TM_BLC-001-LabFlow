<script lang="ts">
    import Nav from "../../components/nav.svelte";
    // @ts-ignore
    import GoPlus from 'svelte-icons/go/GoPlus.svelte';
    // @ts-ignore
    import IoMdSettings from 'svelte-icons/io/IoMdSettings.svelte';
    import { getRol } from '$lib/globalFunctions';
    import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';

    // achtergrond en klikbaar maken van instellingen gebaseerd op rol
    let bgColor = 'bg-blue-400';
    let pointerEvent = 'pointer-events-auto';
    const rol = getRol();
    if (rol !== 'admin') {
        bgColor = 'bg-gray-400';
        pointerEvent = 'pointer-events-none';
    }

    // fetchen van stalen
    let stalen: any[] = [];
    let filteredStalen: any[] = []; // to hold the filtered results
    let searchCode = '';
    let searchDate = '';

    async function loadData() {
        const token = getCookie('authToken') || '';

        if (token) {
            try {
                stalen = await fetchAll(token, 'stalen');
                filteredStalen = stalen; // Initialize filteredStalen with all stalen
                console.log(stalen);
            } catch (error) {
                console.error("data kon niet gefetched worden:", error);
            }
        } else {
            console.error("jwt error");
            goto('/login');
        }
    }

    loadData();

    // Function om te filteren op staalcode en datum
    function filterStalen() {
        filteredStalen = stalen.filter(staal => {
            const codeMatch = staal.staalCode.toString().toLowerCase().includes(searchCode.toLowerCase());

            // Converteren searchdate en aanmaakdatum naar een datumobject
            const searchDateObject = new Date(searchDate);
            const aanmaakDatumObject = new Date(staal.aanmaakDatum);

            const isValidDate = !isNaN(searchDateObject.getTime());

            // Date match: vergelijken van de datums, alleen als de searchDate een geldige datum is
            const dateMatch = isValidDate ? 
                (searchDateObject.toDateString() === aanmaakDatumObject.toDateString()) : true;

            return codeMatch && dateMatch;
        });
    }

    function deleteFilters() {
        searchCode = '';
        searchDate = '';
        filterStalen();
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
        <!-- filteren op code en datum -->
        <div class="flex space-x-5 mb-5">
            <!-- https://learn.svelte.dev/tutorial/text-inputs -->
            <input 
                type="text" 
                id="searchCode" 
                name="searchCode" 
                placeholder="zoeken op code" 
                bind:value={searchCode} on:input={filterStalen} 
                class="w-2/5 h-12 rounded-lg text-black pl-3">
            <input 
                type="date" 
                id="searchDate" 
                name="searchDate" 
                bind:value={searchDate} 
                on:input={filterStalen} 
                class="w-2/5 h-12 rounded-lg text-black px-3" 
            />
            <!-- verwijder filters -->
            <button class="bg-blue-600 rounded-lg p-3 text-white h-12 w-1/5" type="button" on:click={deleteFilters}>Verwijder filters</button>
        </div>

        <div class="space-y-3">
            {#each filteredStalen as staal}
                <div class="grid grid-cols-7 gap-4 bg-white rounded-lg h-16 items-center px-3">
                    <div>
                        <p class="text-gray-400">Code</p>
                        <p>{staal?.staalCode || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Aanmaakdatum</p>
                        <p>{new Date(staal?.aanmaakDatum).toLocaleDateString() || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Naam</p>
                        <p>{staal?.patientAchternaam || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Voornaam</p>
                        <p>{staal?.patientVoornaam || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Geslacht</p>
                        <p>{staal?.patientGeslacht === 'V' ? 'Vrouw' : 'Man'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400">Geboortedatum</p>
                        <p>{new Date(staal?.patientGeboorteDatum).toLocaleDateString() || 'Loading...'}</p>
                    </div>
                    <div>
                        <p class="text-gray-400 font-bold">Laborant</p>
                        <p>{staal?.user.fullName || 'Loading...'}</p>
                    </div>
                </div>
            {/each}
        </div>
    </div>
</div>
