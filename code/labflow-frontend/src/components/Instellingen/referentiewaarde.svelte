<script>
	// Expect the 'option' prop that will be passed from the parent
	export let option;
	export let gap = '5pt';
</script>

<span style="gap: {gap}">
	<!-- Display the 'waarde' of the referentiewaarde (option) -->
	{option.waarde}
	<!-- Assuming 'option' has a 'waarde' field -->
</span>

<style>
	span {
		display: flex;
		align-items: center;
	}
</style>
