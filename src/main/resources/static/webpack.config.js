const path = require("path");
module.exports = {
    entry : {
        tsTest : path.resolve(__dirname, 'js/app/tsTest.ts'),
    },
    resolve: {
        extensions : ['.ts','.js']
    },
    module : {
        rules : [
            {
                test : /\.ts$/,
                use : 'ts-loader',
                include : [path.resolve(__dirname,'js')],
            },
            {
                test: /(\.ejs|\.html)$/,
                loader: 'ejs-loader',
                options: {
                    esModule: false
                }
            }
        ]
    },
    output : {
        path : path.resolve(__dirname, 'dist'),
        publicPath : '/dist',
        filename : '[name].bundle.js',
    },
    devServer:{
        static:'./dist',
        hot:true,
        host: "127.0.0.1",
        port: 3000,
        proxy : {
            "**" : "http://localhost:8080",
        }
    },
}
