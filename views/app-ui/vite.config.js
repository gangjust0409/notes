import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite';
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers';
import path from 'path';

// https://vitejs.dev/config/
export default defineConfig({
  base: "./",
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  plugins: [
    vue(),
    Components({
      resolvers: [AntDesignVueResolver({
        importStyle: 'less'
      })],
    }),

  ],
  css: {
    preprocessorOptions: {
      less: {
        modifyVars: { // 在这里自定义主题色等样式
          'primary-color': '#409eff',
          'link-color': '#409eff',
          'border-radius-base': '2px',
        },
        javascriptEnabled: true,
      },
    },
  },
  // server: {
  //   port: 5173,
  //   proxy: {
  //     // 带选项写法：http://localhost:5173/api/bar -> http://jsonplaceholder.typicode.com/bar
  //     '/api': {
  //       target: 'http://192.168.1.101:9003',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\/api/, ''),
  //     },
  //   }
  // },
})
