package org.azsoftware.handbrake.wrapper;

/**
 * Created by Andrej Kovac (kameecoding) <andrej.kovac.ggc@gmail.com> on 2017-06-17.
 */
public enum HandbrakeCLIOptions {

	/**
	 * General Options
	 */
	help("-h", "--help", "Print Help"),
	version(null, "--version", "Print Version"),
	verbose("-v", "--verbose", "Be verbose (optional argument: logging level)"),
	preset("-Z", "--preset", "Select preset by name (case-sensitive). Enclose names containing spaces in double quotation marks (e.g. \"Preset Name\")", true),
	presetList("-z", "--preset-list", "List available presets"),

	/**
	 * Source Options
	 */
	input("-i", "--input", "Set input file or device (\"source\")", true),
	title("-t", "--title", "Select a title to encode (0 to scan all titles only, default: 1)", true),
	minDuration(null, "--min-duration", "Set the minimum title duration (in seconds). Shorter titles will be ignored (default: 10).", true),
	scan(null, "--scan", "Scan selected title only."),
	mainFeature(null, "--main-feature", "Detect and select the main feature title."),
	chapters("-c", "--chapters", "Select chapters (e.g. \"1-3\" for chapters 1 to 3 or \"3\" for chapter 3 only, default: all chapters)", true),
	angle(null, "--angle", "Select the video angle (DVD or Blu-ray only)", true),
	previews(null, "--previews", "Select how many preview images are generated, and whether to  store to disk (0 or 1). (default: 10:0)", true),
	startAtPreview(null, "--start-at-preview", "Start encoding at a given preview.", true),
	startAt(null, "--start-at", "Start encoding at a given duration (in seconds), frame, or pts (on a 90kHz clock) (e.g. duration:10, frame:300, pts:900000)", true),
	stopat(null, "--stop-at", "Stop encoding at a given duration (in seconds), frame, or pts (on a 90kHz clock) (e.g. duration:10, frame:300, pts:900000)", true),

	/**
	 * Destination Options
	 */
	output("-o", "--output", "Set destination file name", true),
	format("-f", "--format", "Select container format:\nav_mp4\nav_mkv\ndefault: auto-detected from destination file name)", true),
	markers("-m", "--markers", "Add chapter markers"),
	noMarkers(null, "--no-markers", "Disable preset chapter markers"),
	optimize("-O", "--optimize", "Optimize MP4 files for HTTP streaming (fast start, s.s. rewrite file to place MOOV atom at beginning)"),
	dontOptimize(null,"--no-optimize", "Disable preset 'optimize'"),
	ipodAtom("-I", "--ipod-atom", "Add iPod 5G compatibility atom to MP4 container"),
	dontIpodAtom(null,"--no-ipod-atom", "Disable iPod 5G atom"),
	openCL("-P", "--use-opencl", "Use OpenCL where applicable"),

	/**
	 * Video Options
	 */
	encoder("-e", "--encoder", "Select video encoder:\nx264\nqsv_h264\nx265\nqsv_h265\nmpeg4\nmpeg2\nVP8\nVP9\ntheora\n", true),
	encoderPreset(null, "--encoder-preset", "Adjust video encoding settings for a particular speed/efficiency tradeoff (encoder-specific)", true),
	encoderPresetList(null, "--encoder-preset-list", "List supported --encoder-preset values for the specified video encoder", true),
	encoderTune(null, "--encoder-tune", "Adjust video encoding settings for a particular type of souce or situation (encoder-specific)", true),
	encoderTuneList(null,"--encoder-tune-list", "List supported --encoder-tune values for the specified video encoder", true),
	encodeOptions("-x", "--encopts", "Specify advanced encoding options in the same style as mencoder (all encoders except theora): option1=value1:option2=value2", true),
	encoderProfile(null, "--encoder-profile", "Ensure compliance with the requested codec profile (encoder-specific)", true),
	encoderProfileList(null, "--encoder-profile-list", "List supported --encoder-profile values for the specified video encoder", true),
	encoderLevel(null, "--encoder-level", "Ensures compliance with the requested codec level (encoder-specific)", true),
	encoderLevelList(null, "--encoder-level-list", "List supported --encoder-level values for the specified video encoder", true),
	quality("-q", "--quality", "Set video quality (e.g. 22.0)", true),
	bitrate("-b", "--vb", "Set video bitrate in kbit/s (default: 1000)", true),
	twoPass("-2", "--two-pass", "Use two-pass mode"),
	noTwoPass(null, "--no-two-pass", "Disable two-pass mode"),
	turbo("-T", "--turbo", "When using 2-pass use \"turbo\" options on the first pass to improve speed (works with x264 and x265)"),
	noTurbo(null, "--no-turbo", "Disable 2-pass mode's \"turbo\" first pass"),
	rate("-r", "--rate", "Set video framerate(5/10/12/15/20/23.976/24/25/29.97/30/48/50/59.94/60/72/75/90/100/120 or a number between 1 and 1000). Be aware that not specifying a framerate lets HandBrake preserve a source's time stamps, potentially creating variable framerate video"),
	vfr("-r", "--vfr", "Select variable, constant or peak-limited frame rate control. VFR preserves the source timing. CFR makes the output constant rate at the rate given by the -r flag (or the source's average rate if no -r is given). PFR doesn't allow the rate to go over the rate specified with the -r flag but won't change the source timing if it's below that rate. If none of these flags are given, the default is --pfr when -r is given and --vfr otherwise"),
	cfr("-r", "--cfr", "Select variable, constant or peak-limited frame rate control. VFR preserves the source timing. CFR makes the output constant rate at the rate given by the -r flag (or the source's average rate if no -r is given). PFR doesn't allow the rate to go over the rate specified with the -r flag but won't change the source timing if it's below that rate. If none of these flags are given, the default is --pfr when -r is given and --vfr otherwise"),
	pfr("-r", "--pfr", "Select variable, constant or peak-limited frame rate control. VFR preserves the source timing. CFR makes the output constant rate at the rate given by the -r flag (or the source's average rate if no -r is given). PFR doesn't allow the rate to go over the rate specified with the -r flag but won't change the source timing if it's below that rate. If none of these flags are given, the default is --pfr when -r is given and --vfr otherwise"),

	/**
	 * Audio Options
	 */
	audioLangList(null, " --audio-lang-list", "Specifiy a comma separated list of audio languages you would like to select from the source title. By default, the first audio matching each language will be added to your output. Provide the language's ISO 639-2 code (e.g. fre, eng, spa, dut, et cetera) Use code 'und' (Unknown) to match all languages.", true),
	allAudio(null,"--all-audio", "Select all audio tracks matching languages in the specified language list (--audio-lang-list). Any language if list is not specified."),
	firstAudio(null,"--first-audio", "Select first audio track matching languages in the specified language list (--audio-lang-list). Any language if list is not specified."),
	audio("-a", "--audio", "Select audio track(s), separated by commas ('none' for no audio0, \"1,2,3\" for multiple tracks, default: first one). Multiple output tracks can be used for one input.",true),
	audioEncoder("-E", "--aencoder", "Select audio encoder(s):av_aac\ncopy:aac\nac3\ncopy:ac3\neac3\ncopy:eac3\ncopy:truehd\ncopy:dts\ncopy:dtshd\nmp3\ncopy:mp3\nvorbis\nflac16\nflac24\ncopy:flac\nopus\ncopy\n\"copy:<type>\" will pass through the corresponding audio track without \nh is \nsupported for the audio type. Separate tracks by commas.\nDefaults:\n\t\tav_mp4   av_aac\n\t\tav_mkv   mp3\n", true),
	audioCopyMask(null, "--audio-copy-mask", "Set audio codecs that are permitted when the \"copy\" audio encoder option is specified (aac/ac3/eac3/truehd/dts/dtshd/mp3/flac) Separated by commas for multiple allowed options.",true),
	audioFallBack(null, "--audio-fallback", "Set audio codec to use when it is not possible to copy an audio track without re-encoding.", true),
	audioBitrate("-B", "--ab", "Set audio track bitrate(s) in kbit/s. (default: determined by the selected codec, mixdown, and samplerate combination). Separate tracks by commas." , true),
	audioQuality("-Q", "--aq ", "Set audio quality metric. Separate tracks by commas.", true),
	audioCompression("-C", "--ac", "Set audio compression metric. (available depending on selected codec) Separate tracks by commas.", true),
	audioMixdown("-6", "--mixdown", "Format(s) for audio downmixing/upmixing:\n\t\tmono\n\t\tleft_only\n\t\tright_only\n\t\tstereo\n\t\tdpl1\n\t\tdpl2\n\t\t5point1\n\t\t6point1\n\t\t7point1\n\t\t5_2_lfe\n Separate tracks by commas. Defaults:\n\t\tav_aac           up to dpl2\n\t\tac3              up to 5point1\n\t\teac3             up to 5point1\n\t\tmp3              up to dpl2\n\t\tvorbis           up to dpl2\n\t\tflac16           up to 7point1\n\t\tflac24           up to 7point1\n\t\topus             up to 7point1\n", true),
	audioNormalizization(null, "--normalize-mix", "Normalize audio mix levels to prevent clipping. Separate tracks by commas.\n0 = Disable Normalization (default)\n\t1 = Enable Normalization\n", true),
	audioRate("-R", "--arate", "Set audio samplerate(s) (8/11.025/12/16/22.05/24/32/44.1/48 kHz) Separate tracks by commas.", true),
	audioDRC("-D", "--drc", "Apply extra dynamic range compression to the audio, making soft sounds louder. Range is 1.0 to 4.0 (too loud), with 1.5 - 2.5 being a useful range. Separate tracks by commas.", true),
	audioGain(null, "--gain", "Amplify or attenuate audio before encoding.  Does NOT work with audio passthru (copy). Values are in dB.  Negative values attenuate, positive values amplify. A 1 dB difference is barely audible." , true),
	audioDither(null, "--adither", "Select dithering to apply before encoding audio:\n\t\tauto (default)\n\t\tnone\n\t\trectangular\n\t\ttriangular\n\t\ttriangular_hp\n\t\ttriangular_ns\nSeparate tracks by commas. Supported by encoder(s):\n\t\tflac16\n" , true),
	audioName("-A", "--aname", "Set audio track name(s). Separate tracks by commas.", true),

	/**
	 * Picture Options
	 */
	width("-w", "--width"," Set storage width in pixels", true),
	height("-l", "--height", "Set storage height in pixels", true),
	crop(null, "--crop", "Set picture cropping in pixels (default: automatically remove black bars)", true),
	looseCrop(null, "--loose-crop", "Always crop to a multiple of the modulus"),
	noLooseCrop(null, "--no-loose-crop", "Disable preset 'loose-crop'"),
	maxHeight("-Y", "--maxHeight", "Set maximum height in pixels", true),
	maxWidth("-X", "--maxWidth ", "Set maximum width in pixels", true),
	nonAnamorphic(null,  "--non-anamorphic", "Set pixel aspect ratio to 1:1"),
	autoAnamorphic(null, "--auto-anamorphic", "Store pixel aspect ratio that maximizes storage resolution"),
	looseAnamorphic(null, "--loose-anamorphic", "Store pixel aspect ratio that is as close as possible to the source video pixel aspect ratio"),
	customAnamorphic(null, "--custom-anamorphic", "Store pixel aspect ratio in video stream and directly control all parameters."),
	displayWidth(null, "--display-width", "Set display width in pixels, for custom anamorphic. This determines the display aspect during playback, which may differ from the storage aspect.", true),
	keepDisplayAspect(null, "--keep-display-aspect", "Preserve the source's display aspect ratio when using custom anamorphic"),
	noKeepDisplayAspect(null, "--no-keep-display-aspect", "Disable preset 'keep-display-aspect'"),
	pixelAspect(null, "--pixel-aspect", "Set pixel aspect for custom anamorphic (--display-width and --pixel-aspect are mutually exclusive.", true),
	ituPar(null, "--itu-par", "Use wider ITU pixel aspect values for loose and custom anamorphic, useful with underscanned sources"),
	noItuPar(null, "--no-itu-par", "Disable preset 'itu-par'"),
	modulus(null, "--modulus", "Set storage width and height modulus Dimensions will be made divisible by this number. (default: set by preset, typically 2)", true),
	colorMatrix("-M", "--color-matrix", "Set the color space signaled by the output:\n\t\t2020\n\t\t709\n\t\t601\n\t\tntsc (same as 601)\n\t\tpal\n(default: auto-detected from source)",true),

	/**
	 * Filter Options
	 */
	combDetect(null, "--comb-detect", "Detect interlace artifacts in frames. If not accompanied by the decomb or deinterlace filters, this filter only logs the interlaced frame count to the activity log. If accompanied by the decomb or deinterlace filters, it causes these filters to selectively deinterlace only those frames where interlacing is detected.\nPresets:\n\t\tpermissive\n\t\tfast\nCustom Format:\n\t\tmode=m:spatial-metric=s:motion-thresh=m:\n\t\tspatial-thresh=s:filter-mode=f:block-thresh=b:\n\t\tblock-width=b:block-height=b:disable=d\nDefault:\n\t\tmode=3:spatial-metric=2:motion-thresh=1:\n\t\tspatial-thresh=1:filter-mode=2:block-thresh=40:\n\t\tblock-width=16:block-height=16\n",true),
	noCombDetect(null, "--no-comb-detect", "Disable preset comb-detect filter"),
	deinterlace("-d", "--deinterlace", "Deinterlace video using libav yadif.\nPresets:\n\t\tskip-spatial\n\t\tbob\n\t\tqsv\n\nCustom Format:\n\t\tmode=m:parity=p\n\nDefault:\n\t\tmode=3\n" ,true),
	noDeinterlace(null, "--no-deinterlace", "Disable preset deinterlace filter"),
	decomb("-5", "--decomb", "Deinterlace video using a combination of yadif, blend, cubic, or EEDI2 interpolation. Presets:\n\t\tbob\n\t\teedi2\n\t\teedi2bob\n Custom Format:\n\t\tmode=m:magnitude-thresh=m:variance-thresh=v:\n\t\tlaplacian-thresh=l:dilation-thresh=d:\n\t\terosion-thresh=e:noise-thresh=n:\n\t\tsearch-distance=s:postproc=p:parity=p\nDefault:\n\t\tmode=7\n", true),
	nodecomb(null, "--no-decomb", "Disable preset decomb filter", true),
	detelecine("-9", "--detelecine", "Detelecine (ivtc) video with pullup filter Note: this filter drops duplicate frames to restore the pre-telecine framerate, unless you specify a constant framerate (--rate 29.97 --cfr)\nCustom Format:\n\t\tskip-left=s:skip-right=s:skip-top=s:\n\t\tskip-bottom=s:strict-breaks=s:plane=p:parity=p:\n\t\tdisable=d\nDefault:\n\t\tskip-left=1:skip-right=1:skip-top=4:\n\t\tskip-bottom=4:plane=0\n", true),
	noDetelecine(null, "--no-detelecine", "Disable preset detelecine filter", true),
	hqdn3d("-8", "--hqdn3d", "Denoise video with hqdn3d filter\nPresets:\n\t\tultralight\n\t\tlight\n\t\tmedium\n\t\tstrong\n\nCustom Format:\n\t\ty-spatial=y:cb-spatial=c:cr-spatial=c:\n\t\ty-temporal=y:cb-temporal=c:cr-temporal=c\n\nDefault:\n\t\ty-spatial=3:cb-spatial=2:cr-spatial=2:\n\t\ty-temporal=2:cb-temporal=3:cr-temporal=3\n", true),
	noHqdn3d(null, "--no-hqdn3d", "Disable preset hqdn3d filter", true),
	nlmeans(null, "--nlmeans", "Denoise video with NLMeans filter\nPresets:\n\t\tultralight\n\t\tlight\n\t\tmedium\n\t\tstrong\nCustom Format:\n\t\ty-strength=y:y-origin-tune=y:y-patch-size=y:\n\t\ty-range=y:y-frame-count=y:y-prefilter=y:\n\t\tcb-strength=c:cb-origin-tune=c:cb-patch-size=c:\n\t\tcb-range=c:cb-frame-count=c:cb-prefilter=c:\n\t\tcr-strength=c:cr-origin-tune=c:cr-patch-size=c:\n\t\tcr-range=c:cr-frame-count=c:cr-prefilter=c\n\nDefault:\n\t\ty-strength=6:y-origin-tune=1:y-patch-size=7:\n\t\ty-range=3:y-frame-count=2:y-prefilter=0:\n\t\tcb-strength=6:cb-origin-tune=1:cb-patch-size=7:\n\t\tcb-range=3:cb-frame-count=2:cb-prefilter=0\n", true),
	noNlmeans(null, "--no-nlmeans", "Disable preset NLMeans filter", true),
	nlmeanstune(null, "--nlmeans-tune", "Tune NLMeans filter to content type\nTunes:\n\t\tnone\n\t\tfilm\n\t\tgrain\n\t\thighmotion\n\t\tanimation\n\t\ttape\n\t\tsprite\nApplies to NLMeans presets only (does not affect custom settings)", true),
	deblock("-7", "--deblock", "Deblock video with pp7 filter\nCustom Format:\n\t\tqp=q:mode=m:disable=d\nDefault:\n\t\tqp=5\n", true),
	noDeblock(null, "--no-deblock", "Disable preset deblock filter", true),
	rotate(null, "--rotate", "Rotate image or flip its axes. angle rotates clockwise, can be one of:\n\t\t0, 90, 180, 270\nhflip=1 flips the image on the x axis (horizontally).\nCustom Format:\n\t\tangle=a:hflip=h:disable=d\nDefault:\n\t\tangle=180:hflip=0\n", true),
	pad(null, "--pad", "Pad image with borders (e.g. letterbox). The padding color may be set (default black). Color may be an HTML color name or RGB value. The position of image in pad may also be set. \nCustom Format:\n\\t\twidth=w:height=h:color=c:x=x:y=y\n", true),
	grayscale("-g", "--grayscale", "Grayscale encoding"),
	noGrayscale(null, "--no-grayscale", "Disable preset 'grayscale'"),

	/**
	 * Subtitle Options
	 */
	subtitleLangList(null, "--subtitle-lang-list", "Specifiy a comma separated list of subtitle languages you would like to select from the source title. By default, the first subtitle matching each language will be added to your output. Provide the language's ISO 639-2 code (e.g. fre, eng, spa, dut, et cetera)", true),
	allSubtitles(null, "--all-subtitles", "Select all subtitle tracks matching languages in the specified language list (--subtitle-lang-list). Any language if list is not specified."),
	firstSubtitle(null, "--first-subtitle", "Select first subtitle track matching languages in the specified language list (--subtitle-lang-list). Any language if list is not specified."),
	subtitle("-s", "--subtitle", "Select subtitle track(s), separated by commas More than one output track can be used for one input. \"none\" for no subtitles. Example: \"1,2,3\" for multiple tracks. A special track name \"scan\" adds an extra first pass. This extra pass scans subtitles matching the language of the first audio or the language selected by --native-language. The one that's only used 10 percent of the time or less is selected. This should locate subtitles for short foreign language segments. Best used in conjunction with --subtitle-forced.", true),
	subtitleForced("-F", "--subtitle-forced", "Only display subtitles from the selected stream if the subtitle has the forced flag set. The values in 'string' are indexes into the subtitle list specified with '--subtitle'. Separate tracks by commas. Example: \"1,2,3\" for multiple tracks. If \"string\" is omitted, the first track is forced.", true),
	subtitleBurned(null, "--subtitle-burned", "\"Burn\" the selected subtitle into the video track. If \"subtitle\" is omitted, the first track is burned. \"subtitle\" is an index into the subtitle list specified with '--subtitle' or \"native\" to burn the subtitle track that may be added by the 'native-language' option.", true),
	subtitleDefault(null, "--subtitle-default", "Flag the selected subtitle as the default subtitle to be displayed upon playback.  Setting no default means no subtitle will be displayed automatically. 'number' is an index into the subtitle list specified with '--subtitle'. \"none\" may be used to override an automatically selected default subtitle track.", true),
	nativeLanguage("-N", "--native-language", "Specifiy your language preference. When the first audio track does not match your native language then select the first subtitle that does. When used in conjunction with --native-dub the audio track is changed in preference to subtitles. Provide the language's ISO 639-2 code (e.g. fre, eng, spa, dut, et cetera)", true),
	nativeDub(null, "--native-dub", "Used in conjunction with --native-language requests that if no audio tracks are selected the default selected audio track will be the first one that matches the --native-language. If there are no matching audio tracks then the first matching subtitle track is used instead."),
	srtFile(null, "--srt-file", "SubRip SRT filename(s), separated by commas.", true),
	srtCodeset(null, "--srt-codeset", "Character codeset(s) that the SRT file(s) are encoded as, separated by commas. If not specified, 'latin1' is assumed. Command 'iconv -l' provides a list of valid codesets.", true),
	srtOffset(null, "--srt-offset", "Offset (in milliseconds) to apply to the SRT file(s), separated by commas. If not specified, zero is assumed. Offsets may be negative.", true),
	srtLang(null, "--srt-lang", "SRT track language as an ISO 639-2 code (e.g. fre, eng, spa, dut, et cetera) If not specified, then 'und' is used. Separate by commas.", true),
	srtDefault(null, "--srt-default", "Flag the selected SRT as the default subtitle to be displayed during playback. Setting no default means no subtitle will be automatically displayed. If 'number' is omitted, the first SRT is the default. 'number' is a 1-based index into the 'srt-file' list", true),
	srtBurn(null, "--srt-burn", "\"Burn\" the selected SRT subtitle into the video track. If 'number' is omitted, the first SRT is burned. 'number' is a 1-based index into the 'srt-file' list", true),

	/**
	 * Intel QuickSync Video Options
	 */

	enableQsvDecoding(null, "--enable-qsv-decoding", "Allow QSV hardware decoding of the video track"),
	disableQsvDecoding(null, "--disable-qsv-decoding", "Disable QSV hardware decoding of the video track, forcing software decoding instead"),
	qsvAsyncDepth(null, "--qsv-async-depth", "Set the number of asynchronous operations that should be performed before the result is explicitly synchronized. Omit 'number' for zero. (default: 4)", true);



	private String shortOpt;
	private String longOpt;
	private String description;
	private boolean hasArgument;

	HandbrakeCLIOptions(String shortOpt, String longOpt, String description) {
		this.shortOpt = shortOpt;
		this.longOpt = longOpt;
		this.description = description;
		this.hasArgument = false;
	}

	HandbrakeCLIOptions(String shortOpt, String longOpt, String description, boolean hasArgument) {
		this.shortOpt = shortOpt;
		this.longOpt = longOpt;
		this.description = description;
		this.hasArgument = hasArgument;
	}


}

