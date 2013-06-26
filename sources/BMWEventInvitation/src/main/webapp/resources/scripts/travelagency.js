/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    $('input[type="file"]').change(function() {
        $(this).parent().submit();
    });
});