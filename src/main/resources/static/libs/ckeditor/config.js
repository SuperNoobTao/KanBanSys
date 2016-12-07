/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    config.filebrowserBrowseUrl= '/libs/ckfinder/ckfinder.html';
    config.filebrowserImageBrowseUrl = '/libs/ckfinder/ckfinder.html?Type=Images';
    config.filebrowserFlashBrowseUrl = '/libs/ckfinder/ckfinder.html?Type=Flash';
    config.filebrowserUploadUrl = '/libs/ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Files';
    config.filebrowserImageUploadUrl = '/libs/ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Images';
    config.filebrowserFlashUploadUrl = '/libs/ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Flash';
    config.toolbar = [
             ['Source', '-', 'Undo', 'Redo', 'Find', 'Replace', '-', 'Bold', 'TextColor', 'BGColor', 'Italic', 'Underline', 'Strike', 'SelectAll', 'RemoveFormat'],
             ['Image', 'jwplayer', 'Flash', 'Table', 'Rule', 'Link', 'Unlink', 'Anchor'],
             ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull', 'Subscript', 'Superscript', 'OrderedList', 'UnorderedList', 'Outdent', 'Indent', 'FitWindow', '-', 'Maximize'],
    ];
};
