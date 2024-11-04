<script lang="ts">

	export let labelName: string = 'Heparine';
	export let color: string = '#000';
</script>

<div class=" border border-gray-200 rounded-xl flex justify-between items-center p-4 my-3">
	<p class="font-bold text-lg">{labelName}</p>
	<p class={`p-6 rounded-full text-white`} style={`background-color: ${color};`}></p>
</div>