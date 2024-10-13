<script lang="ts">
    import Button from './Button.svelte';
    import Input from './Input.svelte';
    import { goto } from '$app/navigation' // https://www.okupter.com/blog/sveltekit-goto

    let email = "";
    let wachtwoord = "";

    const handleSubmit = async (event: SubmitEvent) => { // SubmitEvent is type of event
        event.preventDefault(); 
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, wachtwoord })
        });

        const result = await response.json();
        console.log(result);
    };

    function goTo() {
        goto('stalen')
    }
</script>

<form class="w-4/6 bg-gray-100 rounded-lg p-8 flex flex-col" on:submit={handleSubmit}>
    <div class="flex justify-between">
        <h1 class="text-gray-900 text-xl font-bold title-font mb-5">Log in met je gegevens</h1>
    </div>
    <Input label="Email" type="text" required bind:value={email} />
    <Input label="Paswoord" type="password" required bind:value={wachtwoord} />
    <Button type="submit" on:click={goTo}>Aanmelden</Button>
</form>
