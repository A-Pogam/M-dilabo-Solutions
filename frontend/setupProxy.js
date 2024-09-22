const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/patients',
    createProxyMiddleware({
      target: 'http://msl_gateway:9091/',  
      changeOrigin: true, 
    })
  );

//      target: 'http://msl_gateway:9091/', 

  app.use(
    '/notes',
    createProxyMiddleware({
      target: 'http://msl_gateway:9091/', 
      changeOrigin: true,
    })
  );

  app.use(
    '/diabetes',
    createProxyMiddleware({
      target: 'http://msl_gateway:9091/', 
      changeOrigin: true,
    })
  ); 
};
