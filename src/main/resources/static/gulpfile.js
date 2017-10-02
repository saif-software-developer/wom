// Include gulp and required plug-ins
var gulp = require('gulp'),
    uglify = require('gulp-uglify'), // minify JS
    rename = require('gulp-rename'), // rename files
    browserify = require('browserify'), // browserify for require functionality
    source = require('vinyl-source-stream'), // for browserify bundle output renaming
    buffer = require('vinyl-buffer'), // for browserify bundle minification
    babelify = require('babelify');
	//Sass/CSS stuff
	sass = require('gulp-sass');

// Browserify and minify compiled JavaScript file for node require functionality
gulp.task('browserify', function() {
	console.log("-> watch me browserify");
    return browserify('js/components/index.jsx')
        .transform(babelify.configure({
            presets: ["env","react"]
        }))
        .bundle()
        .pipe(source('app.bundle.js'))
        .pipe(buffer()) // convert from streaming to buffered vinyl file object
        .pipe(gulp.dest('js/dist/'));
    console.log("-> done with browserify");
});

// Watch task, to execute just type 'gulp watch'
gulp.task('watch',function() {
	gulp.watch('js/sass/**', ['sass']);
    gulp.watch('js/components/**', ['browserify']);
});

gulp.task('sass', function () {
	  console.log("-> watch me getting Sassy");
	  return gulp.src('js/sass/app.scss')
	    .pipe(sass().on('error', sass.logError))
	    .pipe(rename('app.css'))
	    .pipe(gulp.dest('js/dist/'));
});

// Default Task, to execute just type 'gulp'
gulp.task('default', ['sass','browserify', 'watch']);