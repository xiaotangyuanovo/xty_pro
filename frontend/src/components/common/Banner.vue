<template>
  <section class="banner">
    <div class="container">
      <div class="banner__wrap">
        <img :src="imgUrl" class="banner__img" :alt="alt">
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'Banner',
  props: {
    imgUrl: { type: String, required: true },
    alt: { type: String, default: 'banner' }
  }
};
</script>

<style scoped>
.banner {
  margin: 16px 0 24px;
}
.banner__wrap {
  /* 限定宽度 + 居中 + 左右留白，通过外层 .container 控制等宽 */
  width: 100%;
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid var(--border);
}
.banner__img {
  width: 100%;
  height: auto;
  display: block;
}
</style>
