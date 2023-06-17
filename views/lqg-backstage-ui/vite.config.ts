import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import path from 'path'
import IconsResolver from 'unplugin-icons/resolver'
import Icons from 'unplugin-icons/vite'
// import ElementPlus from 'unplugin-element-plus/vite'

const pathSrc = path.resolve(__dirname, './src');

// https://vitejs.dev/config/
export default defineConfig({
  base: "./",
  resolve: {
    alias: {
      '~/': path.resolve(__dirname, './'),
      '@': pathSrc
    }
  },
  // css: {
  //   preprocessorOptions: {
  //     scss: {
  //       additionalData: `@use '~/style/index.scss' as *;`
  //     }
  //   },
  // },
  plugins: [vue(), AutoImport({
    resolvers: [
      IconsResolver({
        prefix: 'Icon',
      }),
      ElementPlusResolver(),// 自动导入图标组件
    ],
    // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
    imports: ['vue'],
    dts: path.resolve(pathSrc, 'auto-imports.d.ts'),
  }),
  Components({
    resolvers: [
      // 自动注册图标组件  使用 IEp为前缀
      IconsResolver({
        enabledCollections: ['ep'],
      }), ElementPlusResolver()],
    dts: path.resolve(pathSrc, 'components.d.ts'),
  }),
  Icons({
    autoInstall: true,
  }),
    // ElementPlus({
    //   useSource: true,
    // }),
  ],
  // server: {
  //   port: 5174,
  //   proxy: {
  //     // 带选项写法：http://localhost:5173/api/bar -> http://jsonplaceholder.typicode.com/bar
  //     '/api': {
  //       target: 'http://localhost:9003/study',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, ''),
  //     },
  //   }
  // }
})
