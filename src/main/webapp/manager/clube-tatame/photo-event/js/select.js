function select(id,description,photographer,w,h) {
  if (window.opener && !window.opener.closed) window.opener.sPhoto(id,description,photographer,w,h);
}