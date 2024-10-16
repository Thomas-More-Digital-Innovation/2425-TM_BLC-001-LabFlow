<script lang="ts">
	import { goto } from '$app/navigation';
    import { getCookie, fetchAll } from '$lib/globalFunctions';

    let stalen: any[] = [];

    async function loadData() {
        
        const token = getCookie('authToken') || '';

        if (token) {
            try {
                stalen = await fetchAll(token, 'stalen');
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
</script>


<div class="space-y-3">
    {#each stalen as staal}
        <div class="grid grid-cols-6 gap-4 bg-white rounded-lg h-16 items-center px-3">
            <div>
                <p class="text-gray-400">Code</p>
                <p>{staal?.staalCode || 'Loading...'}</p>
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

