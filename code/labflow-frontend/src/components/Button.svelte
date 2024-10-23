<script lang="ts">
	// specify type for ts
	export const type: string = 'button';
</script>

<button
	on:click
	class="text-white bg-blue-500 hover:bg-blue-600 border-0 py-2 px-8 focus:outline-none rounded-xl text-lg"
>
	<slot />
</button>
