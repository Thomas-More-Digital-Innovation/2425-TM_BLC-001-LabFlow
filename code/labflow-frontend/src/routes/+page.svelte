<script>
    import Login from '../components/Login.svelte'
    // https://kit.svelte.dev/docs/images
    import loginphoto from '$lib/assets/lab_login.jpg'
</script>

<div class="flex h-screen">
    <div class="w-1/2">
        <img src={loginphoto} alt="laboranten aan het werk" class="w-full h-full object-cover" />
    </div>
    <div class="w-1/2 flex justify-center items-center">
        <Login />
    </div>
</div>