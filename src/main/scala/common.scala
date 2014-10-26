package object common {
  def using[T <: { def close() }] (resource: T) (func: T => Unit) =
    try { func(resource) } finally { if (resource != null) resource.close() }

  def usage[A, B <: {def close(): Unit}] (closeable: B) (f: B => A): A =
    try { f(closeable) } finally { closeable.close() }
}