<!-- https://svelte.dev/repl/b4ef93f914f148e4aa6c764a34c0a4c7?version=3.47.0 -->
<script lang="ts">
	import { setContext } from "svelte"
	const modalId = $$props.$$slots.default[0].name;
	setContext("modalId", modalId)
</script>

<div>
	<slot />
</div>
