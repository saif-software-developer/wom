// Include gulp and required plug-ins
var gulp = require('gulp'),
    uglify = require('gulp-uglify'), // minify JS
    rename = require('gulp-rename'), // rename files
    browserify = require('browserify'), // browserify for require functionality
    source = require('vinyl-source-stream'), // for browserify bundle output renaming
    buffer = require('vinyl-buffer'), // for browserify bundle minification
    babelify = require('babelify');

// Browserify and minify compiled JavaScript file for node require functionality
gulp.task('browserify', function() {
    return browserify('js/Main.jsx')
        .transform(babelify.configure({
            presets: ["es2015","react","stage-0"]
        }))
        .bundle()
        .pipe(source('bundle.js'))
        .pipe(buffer()) // convert from streaming to buffered vinyl file object
        .pipe(rename('bundle.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('js/'));
});

// Watch task, to execute just type 'gulp watch'
gulp.task('watch',function() {
    gulp.watch('js/**', ['browserify']);
});

// Default Task, to execute just type 'gulp'
gulp.task('default', [ 'browserify', 'watch']);