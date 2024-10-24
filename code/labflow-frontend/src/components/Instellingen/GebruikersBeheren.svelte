<script lang="ts">
	import { goto } from '$app/navigation';
	// @ts-ignore
	import FaArrowLeft from 'svelte-icons/fa/FaArrowLeft.svelte';
	// @ts-ignore
	import FaPlus from 'svelte-icons/fa/FaPlus.svelte';
	// @ts-ignore
	import GoX from 'svelte-icons/go/GoX.svelte';
	// @ts-ignore
	import FaTrashAlt from 'svelte-icons/fa/FaTrashAlt.svelte';
	import { onMount } from 'svelte';
	import { fetchUsers } from '$lib/fetchFunctions';
	import { getCookie } from '$lib/globalFunctions';
	import { fetchRollen } from '$lib/fetchFunctions';

	const token = getCookie('authToken') || '';

	let users: any[] = [];
	let rollen: any[] = [];

	onMount(async () => {
		const resultUsers = await fetchUsers();
		if (resultUsers) {
			users = resultUsers;
		}
		const resultRollen = await fetchRollen();
		if (resultRollen) {
			rollen = resultRollen;
			console.log(resultRollen);
		}
	});

	onMount(async () => {
		const resultUsers = await fetchUsers();
		if (resultUsers) {
			users = resultUsers.map((user: any) => ({
				...user,
				newWachtwoord: ''
			}));
		}
		const resultRollen = await fetchRollen();
		if (resultRollen) {
			rollen = resultRollen;
			console.log(resultRollen);
		}
	});

	///// DELETE verwijderen van een gebruiker /////
	let errorMessageGebruikerDELETE = '';
	async function deleteUser(id: string) {
		if (id === '1' || id === '2') {
			console.log('Deze gebruiker kan niet worden verwijderd.');
			errorMessageGebruikerDELETE = 'Deze gebruiker kan niet worden verwijderd.';
			return;
		} else {
			try {
				const response = await fetch(`http://localhost:8080/deleteuser/${id}`, {
					method: 'DELETE',
					headers: {
						'Content-Type': 'application/json',
						Authorization: 'Bearer ' + token
					}
				});
				const result = await fetchUsers();
				if (result) {
					users = result;
				}
			} catch (error) {
				console.error('Gebruiker kon niet worden verwijderd: ', error);
			}
		}
	}

	///// POST aanmaken van een gebruiker /////
	let voornaam = '';
	let achternaam = '';
	let email = '';
	let wachtwoord = '';
	let rol = '';

	let errorVeldenGebruikerPOST = {
		voornaam: false,
		achternaam: false,
		email: false,
		wachtwoord: false,
		rol: false
	};

	let errorMessageGebruikerPOST = '';

	async function nieuweGebruiker() {
		errorVeldenGebruikerPOST = {
			voornaam: false,
			achternaam: false,
			email: false,
			wachtwoord: false,
			rol: false
		};
		let isValid = true;
		if (!voornaam) {
			errorVeldenGebruikerPOST.voornaam = true;
			isValid = false;
		}
		if (!achternaam) {
			errorVeldenGebruikerPOST.achternaam = true;
			isValid = false;
		}
		if (!email) {
			errorVeldenGebruikerPOST.email = true;
			isValid = false;
		}
		if (!wachtwoord) {
			errorVeldenGebruikerPOST.wachtwoord = true;
			isValid = false;
		}
		if (!rol) {
			errorVeldenGebruikerPOST.rol = true;
			isValid = false;
		}
		if (!isValid) {
			errorMessageGebruikerPOST = 'Vul alle verplichte velden in.';
			return;
		}

		try {
			await fetch(`http://localhost:8080/register`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + token
				},
				body: JSON.stringify({
					voorNaam: voornaam,
					achterNaam: achternaam,
					email: email,
					wachtwoord: wachtwoord,
					rol: {
						id: rol
					}
				})
			});
			voornaam = '';
			achternaam = '';
			email = '';
			wachtwoord = '';
			rol = '';
		} catch (error) {
			console.error('Gebruiker kon niet worden aangemaakt: ', error);
		}
		const result = await fetchUsers();
		if (result) {
			users = result;
		}
		return;
	}

	///// PUT aanpassen van een gebruiker /////
	let errorMessageGebruikerPUT = '';

	let errorVeldenGebruikerPUT = {
		voornaam: false,
		achternaam: false,
		email: false,
		wachtwoord: false,
		rol: false
	};

	function updateGebruiker(id: string, newWachtwoord: string) {
		console.log(id, newWachtwoord);

		fetch(`http://localhost:8080/api/users/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + token
			},
			body: JSON.stringify({
				// Include other fields that may have changed
				wachtwoord: newWachtwoord
			})
		})
			.then((response) => {
				if (response.ok) {
					// Handle success, e.g., show a notification
				} else {
					// Handle error
				}
			})
			.catch((error) => {
				console.error('Error updating user:', error);
			});
	}
</script>

<div class="flex flex-col w-full ml-5">
	<div class="flex flex-row justify-between w-full h-14 mb-5">
		<h1 class="font-bold text-3xl">Gebruikers beheren</h1>
		<button
			type="button"
			on:click={async () => {
				await goto('/stalen');
			}}
			class="bg-gray-400 text-xl rounded-lg p-3 text-white h-12 w-32 justify-center items-center flex"
		>
			<div class="w-4 h-4 mr-2"><FaArrowLeft /></div>
			<p>Terug</p>
		</button>
	</div>

	<div class="bg-slate-200 w-full h-full rounded-2xl p-5">
		<div class="space-y-3">
			<!-- Header -->
			<div class="grid grid-cols-6 gap-4 bg-gray-300 rounded-lg h-10 items-center px-3 font-bold">
				<div class="col-span-1">
					<p>Voornaam</p>
				</div>
				<div class="col-span-1 text-left">
					<p>Achternaam</p>
				</div>
				<div class="col-span-1 text-left">
					<p>Email</p>
				</div>
				<div class="col-span-1 text-left">
					<p>Wachtwoord</p>
				</div>
				<div class="col-span-1 text-left">
					<p>Rol</p>
				</div>
				<div class="col-span-1 text-right">
					<p>Acties</p>
				</div>
			</div>

			{#if errorMessageGebruikerPOST}
				<div class="text-red-500 mb-2">{errorMessageGebruikerPOST}</div>
			{/if}
			{#if errorMessageGebruikerDELETE}
				<div class="text-red-500 mb-2">{errorMessageGebruikerDELETE}</div>
			{/if}
			<div class="grid grid-cols-6 bg-white rounded-lg h-20 items-center px-3 shadow-md space-x-3">
				<div class="col-span-1">
					<input
						type="text"
						id="nieuwecategorie"
						bind:value={voornaam}
						class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full
                    {errorVeldenGebruikerPOST.voornaam ? 'border-2 border-red-500' : ''}"
					/>
				</div>
				<div class="col-span-1">
					<input
						type="text"
						id="nieuwecategorie"
						bind:value={achternaam}
						class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full
                    {errorVeldenGebruikerPOST.achternaam ? 'border-2 border-red-500' : ''}"
					/>
				</div>
				<div class="col-span-1">
					<input
						type="text"
						id="nieuwecategorie"
						bind:value={email}
						class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full
                    {errorVeldenGebruikerPOST.email ? 'border-2 border-red-500' : ''}"
					/>
				</div>
				<div class="col-span-1">
					<input
						type="password"
						id="nieuwecategorie"
						bind:value={wachtwoord}
						class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full
                    {errorVeldenGebruikerPOST.wachtwoord ? 'border-2 border-red-500' : ''}"
					/>
				</div>
				<div class="flex flex-col justify-center col-span-1">
					<div>
						{#each rollen as rolItem (rolItem.id)}
							<label
								class="container mr-5 {errorVeldenGebruikerPOST.rol
									? 'text-red-500 font-bold'
									: ''}"
							>
								<input type="radio" name="radio" bind:group={rol} value={rolItem.id} />
								{rolItem.naam}
							</label>
						{/each}
					</div>
				</div>
				<!-- Acties -->
				<div class="col-span-1 flex justify-end">
					<button
						type="button"
						class="h-10 w-10 bg-green-500 p-2 rounded-lg text-white"
						on:click={nieuweGebruiker}
						aria-label="Nieuwe categorie toevoegen"
					>
						<FaPlus />
					</button>
				</div>
			</div>
			{#if errorMessageGebruikerPUT}
				<div class="text-red-500 mb-2">{errorMessageGebruikerPUT}</div>
			{/if}
			<div class="space-y-3">
				{#each users as user, index}
					<div
						class="grid grid-cols-6 bg-white rounded-lg h-20 items-center px-3 shadow-md space-x-3"
					>
						<div class="col-span-1">
							<input
								type="text"
								on:blur={() => updateGebruiker(user.id, user.newWachtwoord)}
								bind:value={user.voorNaam}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-1">
							<input
								type="text"
								on:blur={() => updateGebruiker(user.id, user.newWachtwoord)}
								bind:value={user.achterNaam}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-1">
							<input
								type="text"
								on:blur={() => updateGebruiker(user.id, user.newWachtwoord)}
								bind:value={user.email}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full p-2"
							/>
						</div>
						<div class="col-span-1">
							<input
								type="password"
								on:blur={() => updateGebruiker(user.id, user.newWachtwoord)}
								bind:value={user.newWachtwoord}
								class="bg-gray-100 rounded-lg h-14 text-lg pl-3 w-full"
							/>
						</div>
						<div class="col-span-1">
							<div>
								{#each rollen as rolItem (rolItem.id)}
									<label
										class="container mr-5 {errorVeldenGebruikerPOST.rol
											? 'text-red-500 font-bold'
											: ''}"
									>
										<!-- value komt van de rollen en de bind group is zoals andere imputs in de form gebind aan de user -->
										<input
											type="radio"
											disabled={user?.id === 1 || user?.id === 2}
											name={`rol-${user.id}`}
											bind:group={user.rol.id}
											value={rolItem.id}
											on:blur={() => updateGebruiker(user.id, user.newWachtwoord)}
										/>
										{rolItem.naam}
									</label>
								{/each}
							</div>
						</div>

						<!-- Acties -->
						<div class="col-span-1 flex justify-end">
							{#if user.confirmDelete}
								<!-- parsen van id als string voor deleteUser -->
								<button
									type="button"
									on:click={() => deleteUser(String(user?.id))}
									class="h-10 w-10 bg-red-500 p-2 rounded-lg text-white
									{user?.id === 1 || user?.id === 2 ? 'hidden' : ''}"
								>
									<FaTrashAlt />
								</button>
							{:else}
								<button
									type="button"
									on:click={() => {
										users.forEach((u, i) => {
											if (i !== index) u.confirmDelete = false;
										});
										user.confirmDelete = true;
									}}
									class="h-10 w-10 bg-red-300 p-2 rounded-lg text-white
									{user?.id === 1 || user?.id === 2 ? 'hidden' : ''}"
								>
									<GoX />
								</button>
							{/if}
						</div>
					</div>
				{/each}
			</div>
		</div>
	</div>
</div>
