<script lang="ts">
	import Button from './Button.svelte';
	import Input from './Input.svelte';
	import { goto } from '$app/navigation'; // https://www.okupter.com/blog/sveltekit-goto

	// https://svelte.dev/examples/if-blocks
	let user = { loggedIn: false };

	let email = '';
	let wachtwoord = '';

	const handleSubmit = async (event: SubmitEvent) => {
		// SubmitEvent is type of event
		event.preventDefault();
		const response = await fetch('http://localhost:8080/login', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ email, wachtwoord })
		});

		let result;

		try {
			result = await response.json();
			const authToken = result.token;
			// zonder httpOnly & sameSite is het onveilig
			document.cookie = `authToken=${authToken};path=/;SameSite=Strict`;
			goTo();
		} catch (error) {
			console.error(error);
			displayError = true;
		}
	};

	function goTo() {
		goto('stalen');
	}
	let displayError = false;
</script>

<form class="w-4/6 bg-gray-100 rounded-lg p-8 flex flex-col" on:submit={handleSubmit}>
	<div class="flex justify-between">
		<h1 class="text-gray-900 text-xl font-bold title-font mb-5">Log in met je gegevens</h1>
	</div>
	{#if displayError === true}
		<p class="text-red-500">Deze combinatie van email en wachtwoord bestaat niet.</p>
	{/if}

	<Input label="Email" type="text" required bind:value={email} />
	<Input label="Wachtwoord" type="password" required bind:value={wachtwoord} />
	<Button type="submit">Aanmelden</Button>
</form>
