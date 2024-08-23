const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/patients',
    createProxyMiddleware({
      target: 'http://localhost:9091', 
      changeOrigin: true,
    })
  );

  app.use(
    '/notes',
    createProxyMiddleware({
      target: 'http://localhost:9091', 
      changeOrigin: true,
    })
  );
};
