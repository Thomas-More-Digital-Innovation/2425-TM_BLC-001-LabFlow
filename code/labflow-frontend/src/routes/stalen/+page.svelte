<script>
    import Nav from "../../components/nav.svelte";
    // @ts-ignore
    import GoPlus from 'svelte-icons/go/GoPlus.svelte';
    // @ts-ignore
    import IoMdSettings from 'svelte-icons/io/IoMdSettings.svelte'
</script>

<Nav />
<div class="px-8 mt-4">
    <div class="flex flex-col space-y-5">
        <div class="bg-blue-400 flex flex-col items-center justify-center w-72 h-72 rounded-2xl">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <GoPlus />
            </div>
            <p class="text-white text-2xl text-center mt-2">Nieuwe staal</p>
        </div>
        <div class="bg-blue-400 flex flex-col items-center justify-center w-72 h-72 rounded-2xl">
            <div class="w-40 h-40 text-white flex items-center justify-center">
                <IoMdSettings />
            </div>
            <p class="text-white text-2xl text-center mt-2">Instellingen</p>
        </div>
    </div>
</div>