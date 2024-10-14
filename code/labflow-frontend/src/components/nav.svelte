<script lang="ts">
	import { goto } from '$app/navigation';
    import logoLabflow from '$lib/assets/labflowLogo.svg';
    // @ts-ignore
    import GoPerson from 'svelte-icons/go/GoPerson.svelte';

    function eraseCookie() {
        // https://stackoverflow.com/questions/2144386/how-to-delete-a-cookie
        document.cookie = "authToken=;expires=" + new Date(0).toUTCString()
    }
    
    function logout() {
        goto('/')
    }
</script>

<div class="py-2 px-8 flex justify-between items-center">
    <img src={logoLabflow} alt='logo Labflow & Thomas More' class="h-10">
    <div class="w-28 h-10 flex justify-between items-center">
        <div class="flex flex-col justify-between h-full">
            <p class="text-sm">student</p>
            <button class="text-xs underline" on:click={() => { logout(); eraseCookie(); }} type="button">
                Uitloggen
            </button>
        </div>
        <div class="w-8 h-8 p-1 bg-slate-400 rounded-full flex items-center justify-center mr-1">
            <GoPerson/>
        </div>
    </div>
</div>

