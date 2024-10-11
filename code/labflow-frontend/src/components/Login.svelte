<script>
    import Button from './Button.svelte'
    import Input from './Input.svelte'
</script>

<form class="lg:w-2/6 md:w-1/2 bg-gray-100 rounded-lg p-8 flex flex-col w-full mt-10 md:mt-0">
    <div class="flex justify-between">
        <h1 class="text-gray-900 text-lg font-medium title-font mb-5">Log in met je gegevens</h1>
    </div>
    <Input label="Email" type="text" required value="" />
    <Input label="Paswoord" type="password" required value=""/>
    <Button>Aanmelden</Button>
</form>